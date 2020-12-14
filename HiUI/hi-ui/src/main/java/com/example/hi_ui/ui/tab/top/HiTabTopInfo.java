package com.example.hi_ui.ui.tab.top;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/***
 * @auhtor 栾桂明
 * @date 2020 年12月 14日
 * @desc 设置tab的具体数据信息的对象
 * @param <Color>  内部使用的颜色信息
 */
public class HiTabTopInfo<Color> {
    // tab的类型
    public enum TabType {
        BITMAP, TEXT
    }

    public Class<? extends Fragment> fragment;

    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;


    public HiTabTopInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public HiTabTopInfo(String name, Color defaultColor,
                        Color tintColor) {
        this.name = name;

        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.TEXT;
    }
}
