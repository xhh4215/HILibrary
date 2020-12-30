package com.example.asproj.fragment.profile

import android.accounts.Account
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
import com.example.common.ui.component.HiBaseFragment
import com.example.common.ui.view.IconFontTextView
import com.example.common.ui.view.loadCircle
import com.example.common.ui.view.loadCorner
import com.example.hi_library.log.utils.HiDisplayUtil
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
import com.example.hi_ui.ui.banner.core.HiBanner
import com.example.hi_ui.ui.banner.core.HiBannerMo

class ProfileFragment : HiBaseFragment() {
    private val REQUEST_CODE_LOGIN_PROFILE = 1001
    private lateinit var itemCourse: IconFontTextView
    private lateinit var itemCollection: IconFontTextView
    private lateinit var itemAddress: IconFontTextView
    private lateinit var itemHistory: IconFontTextView
    private lateinit var userName: TextView
    private lateinit var userAvatar: ImageView
    private lateinit var loginDesc: TextView
    private lateinit var tabItemCollection: TextView
    private lateinit var tabItemHistory: TextView
    private lateinit var tabItemLearn: TextView
    private lateinit var loginBanner: HiBanner
    private lateinit var notifyCount: TextView
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
        itemCourse = layoutView.findViewById(R.id.item_course)
        itemCourse.setText(R.string.if_notify)
        itemCourse.append(ITEM_PLACE_HOLDER + getString(R.string.item_course_text))

        itemCollection = layoutView.findViewById(R.id.item_collection)
        itemCollection.setText(R.string.if_collection)
        itemCollection.append(ITEM_PLACE_HOLDER + getString(R.string.item_collection_text))

        itemAddress = layoutView.findViewById(R.id.item_address)
        itemAddress.setText(R.string.if_address)
        itemAddress.append(ITEM_PLACE_HOLDER + getString(R.string.item_address_text))

        itemHistory = layoutView.findViewById(R.id.item_history)
        itemHistory.setText(R.string.if_history)
        itemHistory.append(ITEM_PLACE_HOLDER + getString(R.string.item_history_text))

        userName = layoutView.findViewById(R.id.user_name)

        userAvatar = layoutView.findViewById(R.id.user_avatar)

        loginDesc = layoutView.findViewById(R.id.login_desc)

        tabItemCollection = layoutView.findViewById(R.id.tab_item_collection)

        tabItemHistory = layoutView.findViewById(R.id.tab_item_history)

        tabItemLearn = layoutView.findViewById(R.id.tab_item_learn)

        loginBanner = layoutView.findViewById(R.id.login_banner)

        notifyCount = layoutView.findViewById(R.id.notify_count)

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
                    if (response.code == HiResponse.SUCCESS && response.data != null && response.data!!.total > 0) {

                        notifyCount.visibility = View.VISIBLE
                        notifyCount.text = response.data!!.total.toString()
                    } else {
                        showToast(response.msg)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    showToast(throwable.message)
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
        userName.text =
            if (userProfile.isLogin) userProfile.userName else getString(R.string.profile_not_login)
        loginDesc.text =
            if (userProfile.isLogin) getString(R.string.profile_login_desc_welcome_back) else getString(
                R.string.profile_login_desc
            )
        tabItemCollection.text = spannableTabItem(
            userProfile.favoriteCount,
            getString(R.string.profile_tab_item_collection)
        )
        if (userProfile.isLogin) {
            userAvatar.loadCircle(userProfile.userIcon)
        } else {
            userAvatar.setImageResource(R.drawable.ic_avatar_default)
            userAvatar.setOnClickListener {
                ARouter.getInstance().build("/account/login")
                    .navigation(activity, REQUEST_CODE_LOGIN_PROFILE)
            }
        }
        tabItemHistory.text =
            spannableTabItem(userProfile.browseCount, getString(R.string.profile_tab_item_history))
        tabItemLearn.text =
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
        loginBanner.setBannerData(R.layout.layout_profile_banner_item, models)
        loginBanner.setBindAdapter { viewHolder, mo, position ->
            if (viewHolder == null || mo == null) return@setBindAdapter
            val imageView = viewHolder.findViewById<ImageView>(R.id.banner_item_imageView)
            imageView.loadCorner(mo.url, HiDisplayUtil.dp2px(10f, resources))
        }
        loginBanner.visibility = View.VISIBLE
        loginBanner.setOnBannerClickListener { viewHolder, bannerMo, position ->
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(bannerNoticeList[position].url))
            startActivity(intent)
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