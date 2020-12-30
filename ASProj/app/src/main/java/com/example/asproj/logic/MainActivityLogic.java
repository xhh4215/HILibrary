package com.example.asproj.logic;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import com.example.asproj.R;
import com.example.asproj.fragment.CategoryFragment;
import com.example.asproj.fragment.FavoriteFragment;
import com.example.asproj.fragment.RecommendFragment;
import com.example.asproj.fragment.home.HomePageFragment;
import com.example.asproj.fragment.profile.ProfileFragment;
import com.example.common.tab.HiFragmentTabView;
import com.example.common.tab.HiTabViewAdapter;
import com.example.hi_ui.ui.tab.bottom.HiTabBottomInfo;
import com.example.hi_ui.ui.tab.bottom.HiTabBottomLayout;
import java.util.ArrayList;
import java.util.List;
public class MainActivityLogic {
    private HiFragmentTabView fragmentTabView;
    private HiTabBottomLayout hiTabBottomLayout;
    private List<HiTabBottomInfo<?>> infoList;
    private ActivityProvider activityProvider;
    private int currentItemIndex;
    private final static String SAVED_CURRENT_ID = "SAVED_CURRENT_ID";

    public MainActivityLogic(ActivityProvider activityProvider, Bundle savedInstanceState) {
        this.activityProvider = activityProvider;
        // fix 不保留活动导致的Fragment重叠的问题
        if (savedInstanceState != null) {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID);
        }
        initTabBottom();

    }

    public HiFragmentTabView getFragmentTabView() {
        return fragmentTabView;
    }

    public HiTabBottomLayout getHiTabBottomLayout() {
        return hiTabBottomLayout;
    }

    public List<HiTabBottomInfo<?>> getInfoList() {
        return infoList;
    }

    private void initTabBottom() {
        hiTabBottomLayout = activityProvider.findViewById(R.id.tab_bottom_layout);
        hiTabBottomLayout.setAlpha(0.85f);
        infoList = new ArrayList<>();
        int defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = activityProvider.getResources().getColor(R.color.tabBottomTintColor);
        HiTabBottomInfo homeInfo = new HiTabBottomInfo("首页",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_home),
                null,
                defaultColor,
                tintColor);
        homeInfo.fragment = HomePageFragment.class;
        HiTabBottomInfo favoriteInfo = new HiTabBottomInfo("收藏",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_favorite),
                null,
                defaultColor,
                tintColor);
        favoriteInfo.fragment = FavoriteFragment.class;
        HiTabBottomInfo categoryInfo = new HiTabBottomInfo("分类",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_category),
                null,
                defaultColor,
                tintColor);
        categoryInfo.fragment = CategoryFragment.class;
        HiTabBottomInfo recommendInfo = new HiTabBottomInfo("推荐",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_recommend),
                null,
                defaultColor,
                tintColor);
        recommendInfo.fragment = RecommendFragment.class;
        HiTabBottomInfo profileInfo = new HiTabBottomInfo("我的",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_profile),
                null,
                defaultColor,
                tintColor);
        profileInfo.fragment = ProfileFragment.class;
        infoList.add(homeInfo);
        infoList.add(favoriteInfo);
        infoList.add(categoryInfo);
        infoList.add(recommendInfo);
        infoList.add(profileInfo);
        hiTabBottomLayout.inflateInfo(infoList);
        initFragmentTabView();
        hiTabBottomLayout.addTabSelectedChangeListener((index, prevInfo, nextInfo) -> {
            fragmentTabView.setCurrentItem(index);
                    MainActivityLogic.this.currentItemIndex = index;
        });
        hiTabBottomLayout.defaultSelected(infoList.get(currentItemIndex));

    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex);
    }


    private void initFragmentTabView() {
        HiTabViewAdapter tabViewAdapter = new HiTabViewAdapter(activityProvider.getSupportFragmentManager(), infoList);
        fragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view);
        fragmentTabView.setAdapter(tabViewAdapter);

    }

    /***
     * 代表activity 必须要提供的一些能力
     */
    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();


        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);
    }
}
