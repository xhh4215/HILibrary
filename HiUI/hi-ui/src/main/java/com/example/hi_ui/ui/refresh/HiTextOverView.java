package com.example.hi_ui.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hi_ui.R;

public class HiTextOverView extends HiOverView {
    private TextView mTextView;
    private View mRotateView;

    public HiTextOverView(@NonNull Context context) {
        super(context);
    }

    public HiTextOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HiTextOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hi_refresh_overview, this, true);
        mRotateView = findViewById(R.id.iv_rotate);
        mTextView = findViewById(R.id.text);

    }

    @Override
    protected void onScroll(int scrollY, int pullRefreshHeight) {

    }

    @Override
    protected void onVisible() {
       mTextView.setText("下拉刷新");
    }

    @Override
    public void onOver() {
        mTextView.setText("松开刷新");

    }

    @Override
    public void onRefresh() {
        mTextView.setText("正在刷新.....");
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_anim);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        animation.setInterpolator(linearInterpolator);
        mRotateView.startAnimation(animation);

    }

    @Override
    public void onFinish() {
        mRotateView.clearAnimation();

    }
}
