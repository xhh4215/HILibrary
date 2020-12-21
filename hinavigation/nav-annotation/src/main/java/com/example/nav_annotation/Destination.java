package com.example.nav_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Destination {
    /**
     * 页面路由名称
     * @return
     */
    String pageUrl();

    /***
     * 是否为路由中的第一个界面
     * @return
     */
    boolean asStarter() default false;
}
