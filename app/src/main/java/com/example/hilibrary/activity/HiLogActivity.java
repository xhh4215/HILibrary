package com.example.hilibrary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.hilibrary.R;
import com.example.hilibrary.log.HiLog;

public class HiLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_log);
        findViewById(R.id.print_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printLog();
            }
        });

    }

    private void printLog() {
        HiLog.a("9900");
    }
}