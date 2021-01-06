package com.example.asproj.fragment.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.example.asproj.R
import com.example.asproj.http.api.accountapi.AccountApi
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.http.model.CourseNotice
import com.example.asproj.http.model.Notice
import com.example.asproj.http.model.UserProfile
import com.example.asproj.rote.HiRoute
import com.example.common.ui.component.HiBaseFragment
import com.example.common.ui.view.IconFontTextView
import com.example.common.ui.view.loadCircle
import com.example.common.ui.view.loadCorner
import com.example.hi_library.log.utils.HiDisplayUtil
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
import com.example.hi_ui.ui.banner.core.HiBanner
import com.example.hi_ui.ui.banner.core.HiBannerMo
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : HiBaseFragment() {
    private val REQUEST_CODE_LOGIN_PROFILE = 1001

     private val ITEM_PLACE_HOLDER = "   "
    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    /***
     * 在布局加载加载完毕的（onCreateView()之后），后立即调用，在视图中恢复任何保存状态之前调用
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置底部四个功能item的入口图标

        item_course.setText(R.string.if_notify)
        item_course.append(ITEM_PLACE_HOLDER + getString(R.string.item_course_text))

        item_collection.setText(R.string.if_collection)
        item_collection.append(ITEM_PLACE_HOLDER + getString(R.string.item_collection_text))

        item_address.setText(R.string.if_address)
        item_address.append(ITEM_PLACE_HOLDER + getString(R.string.item_address_text))

        item_history.setText(R.string.if_history)
        item_history.append(ITEM_PLACE_HOLDER + getString(R.string.item_history_text))
        queryCourseNoticeData()

        queryLoginUserData()

    }

    /***
     * 查询登录的用户信息
     */
    private fun queryLoginUserData() {
        ApiFactory.create(AccountApi::class.java).profile()
            .enqueue(object : HiCallBack<UserProfile> {
                override fun onSuccess(response: HiResponse<UserProfile>) {
                    val userProfile = response.data
                    if (response.code == HiResponse.SUCCESS && userProfile != null) {
                        updateUI(userProfile)
                    } else {
                        showToast(response.msg)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    showToast(throwable.message)
                }

            })
    }

    /***
     * 获取课程的通知信息
     */
    private fun queryCourseNoticeData() {
        ApiFactory.create(AccountApi::class.java).notice()
            .enqueue(object : HiCallBack<CourseNotice> {
                override fun onSuccess(response: HiResponse<CourseNotice>) {
                    if (response.data != null && response.data!!.total > 0) {
                        notify_count.visibility = View.VISIBLE
                        notify_count.text = response.data!!.total.toString()
                    }
                }

                override fun onFailed(throwable: Throwable) {
                }

            })
    }

    /**
     * 吐司提示
     */
    private fun showToast(message: String?) {
        if (message == null) {
            return
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

    /***
     * 更新UI的操作
     */
    private fun updateUI(userProfile: UserProfile) {
        user_name.text =
            if (userProfile.isLogin) userProfile.userName else getString(R.string.profile_not_login)
        login_desc.text =
            if (userProfile.isLogin) getString(R.string.profile_login_desc_welcome_back) else getString(
                R.string.profile_login_desc
            )
        tab_item_collection.text = spannableTabItem(
            userProfile.favoriteCount,
            getString(R.string.profile_tab_item_collection)
        )
        if (userProfile.isLogin) {
            user_avatar.loadCircle(userProfile.userIcon)
        } else {
            user_avatar.setImageResource(R.drawable.ic_avatar_default)
            user_avatar.setOnClickListener {
                ARouter.getInstance().build("/account/login")
                    .navigation(activity, REQUEST_CODE_LOGIN_PROFILE)
            }
        }
        tab_item_history.text =
            spannableTabItem(userProfile.browseCount, getString(R.string.profile_tab_item_history))
        tab_item_learn.text =
            spannableTabItem(userProfile.learnMinutes, getString(R.string.profile_tab_item_learn))
        updateBanner(userProfile.bannerNoticeList)
    }

    /***
     * 更新Banner
     */
    private fun updateBanner(bannerNoticeList: List<Notice>?) {
        if (bannerNoticeList == null || bannerNoticeList.isEmpty()) return
        var models = mutableListOf<HiBannerMo>()
        bannerNoticeList.forEach {
            val hiBannerMo = object : HiBannerMo() {}
            hiBannerMo.url = it.cover
            models.add(hiBannerMo)
        }
        login_banner.setBannerData(R.layout.layout_profile_banner_item, models)
        login_banner.setBindAdapter { viewHolder, mo, position ->
            if (viewHolder == null || mo == null) return@setBindAdapter
            val imageView = viewHolder.findViewById<ImageView>(R.id.banner_item_imageView)
            imageView.loadCorner(mo.url, HiDisplayUtil.dp2px(10f, resources))
        }
        login_banner.visibility = View.VISIBLE
        login_banner.setOnBannerClickListener { viewHolder, bannerMo, position ->
            HiRoute.startActivity4Browser(bannerNoticeList[position].url)
        }
    }

    /**
     * 游览记录 收藏等文本的处理
     */
    private fun spannableTabItem(topText: Int, bottomText: String): CharSequence? {
        val spanStr = topText.toString()
        var ssb = SpannableStringBuilder()
        var ssTop = SpannableString(spanStr)
        val spanFlag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ssTop.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color_000)),
            0,
            ssTop.length,
            spanFlag
        )
        ssTop.setSpan(AbsoluteSizeSpan(18, true), 0, ssTop.length, spanFlag)
        ssTop.setSpan(StyleSpan(Typeface.BOLD), 0, ssTop.length, spanFlag)
        ssb.append(ssTop)
        ssb.append(bottomText)
        return ssb
    }

    /***
     * 登录成功数据的刷新
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_LOGIN_PROFILE && resultCode == Activity.RESULT_OK && data != null) {
            queryLoginUserData()
        }

    }

}