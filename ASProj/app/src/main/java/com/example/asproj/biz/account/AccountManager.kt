package com.example.asproj.biz.account

import android.app.Application
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.http.api.accountapi.AccountApi
import com.example.asproj.http.model.UserProfile
import com.example.common.utils.SPUtil
import com.example.hi_library.cache.HiStorage
import com.example.hi_library.executor.HiExecutor
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
import com.example.hi_library.utils.AppGlobals
import org.w3c.dom.Text

/***
 * @author 栾桂明
 * @date  2020年 1月 21日
 * @desc  用户的账户管理
 */
object AccountManager {
    // 用户信息
    private var userProfile: UserProfile? = null

    // 获取是否登录的 boarding_pass 的key
    private val KEY_BOARDING_PASS = "key_boarding_pass"

    // 获取用户的信息的key
    private val KEY_USER_PROFILE = "user_profile"

    // 登录成功之后获取的boardingPass
    private var boardingPass: String? = null

    // 是否正在拉取
    @Volatile
    private var isFetching = false

    // 保存登录状态的livedata数据
    private val loginLiveData = MutableLiveData<Boolean>()
    private val profileLiveData = MutableLiveData<UserProfile>()

    //存储未能自动取消观察的observer
    private val loginForeverObservers = mutableListOf<Observer<Boolean>>()
    private val profileForeverObservers = mutableListOf<Observer<UserProfile?>>()

    /***
     * 登录的方法
     */
    fun login(context: Context? = AppGlobals.get(), observer: Observer<Boolean>) {
        //判断是否能感知宿主的生命周期
        if (context is LifecycleOwner) {
            //实现观察者的自动的注册和反注册
            loginLiveData.observe(context, observer)

        } else {
            loginLiveData.observeForever(observer)
            //将无法自动取消注册的观察者添加到一个集合中统一取消注册
            loginForeverObservers.add(observer)
        }

        val intent = Intent(context, LoginActivity::class.java)
        // 如果是Application启动是需添加一个Flag
        if (context is Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (context == null) {
            throw IllegalStateException("context must be not null")
        }
        context.startActivity(intent)


    }

    /***
     * 是否已经登录
     */
    fun isLogin(): Boolean = !TextUtils.isEmpty(boardingPass)

    /***
     * 登录成功的操作
     */
    fun loginSuccess(boardingPass: String) {
        //保存登录成功获取的boardingPass
        SPUtil.putString(KEY_BOARDING_PASS, boardingPass)
        this.boardingPass = boardingPass
        //通过liveData将数据发送给观察者
        loginLiveData.value = true
        //清除未能自动取消注册的观察者
        clearLoginForeverObserver()
    }

    /***
     * 清除所有的观察者对象
     */
    private fun clearLoginForeverObserver() {
        for (observer in loginForeverObservers) {
            //移除观察者
            loginLiveData.removeObserver(observer)
        }
        loginForeverObservers.clear()
    }

    /***
     * 返回一个boardingPass字符串
     */
    fun getBoardingPass(): String? {
        if (TextUtils.isEmpty(boardingPass)) {
            boardingPass = SPUtil.getString(KEY_BOARDING_PASS)
        }
        return boardingPass
    }

    /****
     * 获取用户信息
     */
    @Synchronized
    fun getUserProfile(
        lifecycleOwner: LifecycleOwner?,
        observer: Observer<UserProfile?>,
        onlyCache: Boolean = true
    ) {
        if (lifecycleOwner == null) {
            profileLiveData.observeForever(observer)
            profileForeverObservers.add(observer)
        } else {
            profileLiveData.observe(lifecycleOwner, observer)
        }
        if (userProfile != null && onlyCache) {
            profileLiveData.postValue(userProfile)
            return
        }
        if (isFetching) return
        isFetching = true
        ApiFactory.create(AccountApi::class.java).profile()
            .enqueue(object : HiCallBack<UserProfile> {
                override fun onSuccess(response: HiResponse<UserProfile>) {
                    userProfile = response.data
                    if (response.code == HiResponse.SUCCESS && userProfile != null) {
                        HiExecutor.execute(runnable = Runnable {
                            HiStorage.saveCache(KEY_USER_PROFILE, userProfile)
                            isFetching = false
                        })
                        profileLiveData.value = userProfile
                    } else {
                        profileLiveData.value = null
                    }
                    clearProfileForeverObserver()
                }

                override fun onFailed(throwable: Throwable) {
                    profileLiveData.value = null
                    isFetching = false
                }

            })
    }

    /***
     * 清除不能自动取消的观察获取用户信息的观察者
     */
    private fun clearProfileForeverObserver() {
        for (observer in profileForeverObservers) {
            profileLiveData.removeObserver(observer)
        }
        profileForeverObservers.clear()
    }
}