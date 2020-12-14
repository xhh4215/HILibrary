package com.example.hi_ui.ui.tab.bottom;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/***
 * @auhtor 栾桂明
 * @date 2020 年12月 14日
 * @desc 设置tab的具体数据信息的对象
 * @param <Color>  内部使用的颜色信息
 */
public class HiTabBottomInfo<Color> {
    // tab的类型
    public enum TabType {
        BITMAP, ICON
    }

    public Class<? extends Fragment> fragment;

    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public String iconFont;
    public String defaultIconName;
    public String selectedIconMame;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;


    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public HiTabBottomInfo(String name, String iconFont, String defaultIconName, String selectedIconMame, Color defaultColor,
                           Color tintColor) {
        this.name = name;
        this.iconFont = iconFont;
        this.defaultIconName = defaultIconName;
        this.selectedIconMame = selectedIconMame;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.ICON;
    }
}
