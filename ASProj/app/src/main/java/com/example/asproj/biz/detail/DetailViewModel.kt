package com.example.asproj.biz.detail

import androidx.lifecycle.*
import com.example.asproj.BuildConfig
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.http.api.detailapi.DetailApi
import com.example.asproj.http.model.DetailModel
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
import java.lang.Exception

class DetailViewModel(val goodId: String?) : ViewModel() {
    companion object {
        /***
         * 创建ViewModel的工厂
         */
        private class DetailViewModelFactory(val goodId: String?) :
            ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                try {
                    val constructor = modelClass.getConstructor(String::class.java)
                    if (constructor != null) {
                        return constructor.newInstance(goodId)
                    }
                } catch (exception: Exception) {

                }
                return super.create(modelClass)
            }

        }

        /***
         * 获取ViewModel的方法
         */
        fun get(goodId: String?, viewModelStoreOwner: ViewModelStoreOwner): DetailViewModel =
            ViewModelProvider(viewModelStoreOwner, DetailViewModelFactory(goodId)).get(
                DetailViewModel::class.java
            )


    }

    /****
     * 获取商品详情的网络请求
     */
    fun queryDetailData(): LiveData<DetailModel?> {
        val pageData = MutableLiveData<DetailModel?>()
        if (goodId != null) {
            ApiFactory.create(DetailApi::class.java).queryDetail(goodId)
                .enqueue(object : HiCallBack<DetailModel> {
                    override fun onSuccess(response: HiResponse<DetailModel>) {
                        if (response.successfull() && response.data != null) {
                            pageData.value = response.data
                        } else {
                            pageData.value = null
                        }
                    }

                    override fun onFailed(throwable: Throwable) {
                        pageData.value = null
                        if (BuildConfig.DEBUG) {
                            throwable.printStackTrace()
                        }
                    }

                })
        }
        return pageData
    }

}