package com.example.hi_library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;

public class HiViewUtil {
    /***
     * 获取指定类型的子View
     * @param group   ViewGroup
     * @param cls  如  RecycleView.class
     * @param <T>
     * @return 指定类型的View
     */
    public static <T> T findTypeView(@Nullable ViewGroup group, Class<T> cls) {
        if (group == null) {

        }
        Deque<View> deque = new ArrayDeque<>();
        deque.add(group);
        while (!deque.isEmpty()) {
            View node = deque.removeFirst();
            if (cls.isInstance(node)) {
                return cls.cast(node);
            } else if (node instanceof ViewGroup) {
                ViewGroup container = (ViewGroup) node;
                for (int i = 0, count = container.getChildCount(); i < count; i++) {
                    deque.add(container.getChildAt(i));
                }
            }
        }
        return null;
    }

    public static boolean isActivityDestroy(Context context) {
        Activity activity = findActivity(context);
        if (activity != null) {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
                return activity.isDestroyed() || activity.isFinishing();
            }
            return activity.isFinishing();
        }
        return true;
    }

    private static Activity findActivity(Context context) {
        //怎么判断一个context  是不是Activity
        if (context instanceof Activity) return (Activity) context;
        else if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }
}
