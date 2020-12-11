package com.example.hilibrary.log.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public class HiDisplayUtil {
    public static int dp2px(float dp, Resources resources) {
                return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.getDisplayMetrics());
    }


    public static int getDisplayWidthInPx(@NotNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return 0;
    }


    public static int getDisplayHeightInPx(@NotNull Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager!=null){
             Display display = windowManager.getDefaultDisplay();
             Point size = new Point();
             display.getSize(size);
             return size.y;
        }
        return 0;
    }
}
