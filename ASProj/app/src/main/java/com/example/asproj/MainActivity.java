package com.example.asproj;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.asproj.logic.MainActivityLogic;
import com.example.asproj.logic.MainActivityLogic.ActivityProvider;
import com.example.common.ui.component.HiBaseActivity;
import com.example.hi_library.utils.HiStatusBarUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MainActivity extends HiBaseActivity implements ActivityProvider {
    private MainActivityLogic activityLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityLogic = new MainActivityLogic(this, savedInstanceState);
        HiStatusBarUtil.INSTANCE.setStatusBar(this, true, Color.WHITE, false);
    }

    /***
     * 执行的时机
     * 1 用户按了返回键
     * 2 切换最近应用的时候
     * 3 按下电源键关闭屏幕的时候
     * 4 屏幕发生旋转的时候
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        activityLogic.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            //点击了音量下键
            if (BuildConfig.DEBUG) {
                try {
                    Class<?> clazz = Class.forName("com.example.hi_debugtool.DebugToolDialogFragment");
                    DialogFragment target = (DialogFragment) clazz.getConstructor().newInstance();
                    target.show(getSupportFragmentManager(),"debug_tool");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}