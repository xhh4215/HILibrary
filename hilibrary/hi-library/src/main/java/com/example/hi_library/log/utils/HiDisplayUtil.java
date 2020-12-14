package com.example.hi_library.log.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import org.jetbrains.annotations.NotNull;

/****
 *
 */
public class HiDisplayUtil {
    /***
     * 将 dp 转化为 px
     * @param dp
     * @param resources
     * @return
     */
    public static int dp2px(float dp, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    /****
     * 获取屏幕的宽度
     * @param context  上下文对象
     * @return
     */
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

    /***
     * 获取屏幕的高度
     * @param context 上下文对象
     * @return
     */
    public static int getDisplayHeightInPx(@NotNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            // 提供有关逻辑显示器的大小和密度的信息。
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            //获取屏幕的大小 以像素为单位
            display.getSize(size);
            return size.y;
        }
        return 0;
    }
}
