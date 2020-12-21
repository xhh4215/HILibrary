package com.example.navcompiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.nav_annotation.Destination;
import com.google.auto.service.AutoService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import jdk.nashorn.api.scripting.JSObject;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.example.nav_annotation.Destination")
public class NavProcessor extends AbstractProcessor {
    private static final String PAGE_TYPE_ACTIVITY = "ACTIVITY";
    private static final String PAGE_TYPE_FRAGMENT = "FRAGMENT";
    private static final String PAGE_TYPE_DIALOG = "DIALOG";
    private static final String OUT_PUT_FILE = "destination.json";
    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "enter init .....");
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Destination.class);
        if (!elements.isEmpty()) {
            HashMap<String, JSONObject> destMap = new HashMap<>();
            handleDestination(destMap, Destination.class, elements);
            try {
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", OUT_PUT_FILE);
                //app/build/intermediates/javac/debug/classes/目录下
                //app/main/assert/
                String resourcePath = resource.toUri().getPath();
                messager.printMessage(Diagnostic.Kind.NOTE, "path......."+resourcePath+"app:index"+resourcePath.indexOf("app"));
                String appPath = resourcePath.substring(0,resourcePath.indexOf("app/")+4);

                String assertPath = appPath + "src/main/assets";
                messager.printMessage(Diagnostic.Kind.NOTE, "assert......."+assertPath);

                File file = new File(assertPath);
                if (!file.exists()) {
                    file.mkdirs();
                    messager.printMessage(Diagnostic.Kind.NOTE, "file  name......."+file.getName());

                }
                String content = JSON.toJSONString(destMap);
                File outputFile = new File(assertPath, OUT_PUT_FILE);
                if (outputFile.exists()) {
                    outputFile.delete();
                }
                outputFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
                writer.write(content);
                writer.flush();
                fileOutputStream.close();
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void handleDestination(HashMap<String, JSONObject> destMap, Class<Destination> destinationClass, Set<? extends Element> elements) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            String className = typeElement.getQualifiedName().toString();
            Destination annotation = typeElement.getAnnotation(destinationClass);
            String pageUrl = annotation.pageUrl();
            boolean asStarter = annotation.asStarter();
            int id = Math.abs(className.hashCode());
            String destType = getDestinationType(typeElement);
            if (destMap.containsKey(pageUrl)) {
                messager.printMessage(Diagnostic.Kind.NOTE, "不同的页面部允许使用相同的pageUrl .....");
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("className", className);
                jsonObject.put("pageUrl", pageUrl);
                jsonObject.put("asStarter", asStarter);
                jsonObject.put("id", id);
                jsonObject.put("destType", destType);
                destMap.put(pageUrl, jsonObject);
            }
        }
    }

    private String getDestinationType(TypeElement typeElement) {
        TypeMirror superclass = typeElement.getSuperclass();
        String superName = superclass.toString();
        if (superName.contains(PAGE_TYPE_ACTIVITY.toLowerCase())) {
            return PAGE_TYPE_ACTIVITY.toLowerCase();
        } else if (superName.contains(PAGE_TYPE_FRAGMENT.toLowerCase())) {
            return PAGE_TYPE_FRAGMENT.toLowerCase();
        } else if (superName.contains(PAGE_TYPE_DIALOG.toLowerCase())) {
            return PAGE_TYPE_DIALOG;
        }
        // 这个父类的类型是类的类型 或者是接口的类型
        if (superclass instanceof DeclaredType) {
            Element element = ((DeclaredType) superclass).asElement();
            if (element instanceof TypeElement) {
                getDestinationType((TypeElement) element);
            }
        }
        return null;
    }
}
