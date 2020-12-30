package com.example.asproj;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.asproj.biz.LoginActivity;

import com.example.asproj.logic.MainActivityLogic;
import com.example.asproj.logic.MainActivityLogic.ActivityProvider;
import com.example.common.manager.ActivityManager;
import com.example.common.ui.component.HiBaseActivity;

public class MainActivity extends HiBaseActivity implements ActivityProvider {
    private MainActivityLogic activityLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.Companion.getInstance().addFrontBackCallback(front -> Toast.makeText(MainActivity.this, "前后天切换", Toast.LENGTH_SHORT).show());
        activityLogic = new MainActivityLogic(this, savedInstanceState);
         startActivity(new Intent(this, LoginActivity.class));
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



}