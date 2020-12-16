package com.example.hi_ui.ui.banner.indicator;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hi_library.log.utils.HiDisplayUtil;
import com.example.hi_ui.R;

/***
 * 圆形指示器
 */
public class HiCircleIndicator extends FrameLayout implements HiIndicator<FrameLayout> {
    private  static final int VWC = ViewGroup.LayoutParams.WRAP_CONTENT;
    /***
     * 正常状态下的指示点
     */
    int mPointNormal = R.drawable.shape_point_normal;
    /***
     * 选中状态下的指示点
     */
    int mPointSelected = R.drawable.shape_point_select;
    /***
     * 指示点左右内间距
     */
    private int mPointLeftRightPadding;
    /***
     * 指示点上下内间距
     */
    private int mPointTopBottomPadding;
    public HiCircleIndicator(@NonNull Context context) {
        this(context,null);
    }

    public HiCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HiCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPointLeftRightPadding = HiDisplayUtil.dp2px(5,getContext().getResources());
        mPointTopBottomPadding = HiDisplayUtil.dp2px(15,getContext().getResources());
    }

    @Override
    public FrameLayout get() {
        return this;
    }

    @Override
    public void onInflate(int count) {
        removeAllViews();
        if (count<0){
            return;
        }
        LinearLayout groupView = new LinearLayout(getContext());
        groupView.setOrientation(LinearLayout.HORIZONTAL);
        ImageView imageView;
        LinearLayout.LayoutParams imgViewParam = new LinearLayout.LayoutParams(VWC,VWC);
        imgViewParam.gravity = Gravity.CENTER_VERTICAL;
        imgViewParam.setMargins(mPointLeftRightPadding,mPointTopBottomPadding,mPointLeftRightPadding,mPointTopBottomPadding);
         for (int i =0;i<count;i++){
             imageView = new ImageView(getContext());
             imageView.setLayoutParams(imgViewParam);
             if (i==0){
                 imageView.setImageResource(mPointSelected);
             }else{
                 imageView.setImageResource(mPointNormal);
             }
             groupView.addView(imageView);
         }
         LayoutParams groupParam = new LayoutParams(VWC,VWC);
         groupParam.gravity = Gravity.CENTER|Gravity.BOTTOM;
         addView(groupView,groupParam);

    }

    @Override
    public void onPointChange(int current, int count) {
        //存放指示器的线性布局
          ViewGroup viewGroup = (ViewGroup) getChildAt(0);
          for (int i=0;i<viewGroup.getChildCount();i++){
              ImageView imageView = (ImageView) viewGroup.getChildAt(i);
              if (i==current){
                  imageView.setImageResource(mPointSelected);
              }else{
                  imageView.setImageResource(mPointNormal);
              }
              //重新布局一下
              imageView.requestLayout();
          }

    }
}
