package com.example.asproj.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.http.api.homeapi.HomeApi
import com.example.asproj.http.model.HomeModel
import com.example.asproj.http.model.TabCategory
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack

class HomeViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {


    fun queryCategoryList(): LiveData<List<TabCategory>?> {
        val liveData = MutableLiveData<List<TabCategory>?>()
        val menuCache = savedStateHandle.get<List<TabCategory>?>("categoryTabs")
        if (menuCache != null) {
            liveData.postValue(menuCache)
            return liveData
        }
        ApiFactory.create(HomeApi::class.java).queryTabList()
            .enqueue(object : HiCallBack<List<TabCategory>> {
                override fun onFailed(throwable: Throwable) {

                }

                override fun onSuccess(response: HiResponse<List<TabCategory>>) {
                    val data = response.data
                    if (response.successfull() && data != null) {
                        liveData.postValue(data)
                        savedStateHandle.set("categoryTabs", data)
                    }
                }

            })
        return liveData
    }


    fun queryTabCategoryList(
        categoryId: String?,
        pageIndex: Int,
        cacheStrategy: Int
    ): LiveData<HomeModel?> {
        val liveData = MutableLiveData<HomeModel?>()
        val categoryListCache = savedStateHandle.get<HomeModel?>("categoryTabList")
        if (categoryListCache != null) {
            liveData.postValue(categoryListCache)
            return liveData
        }
        ApiFactory.create(HomeApi::class.java)
            .queryTabCategoryList(cacheStrategy, categoryId!!, pageIndex, 10)
            .enqueue(object : HiCallBack<HomeModel> {
                override fun onSuccess(response: HiResponse<HomeModel>) {
                    val data = response.data
                    if (response.successfull() && response.data != null) {
                        liveData.postValue(data)
                        savedStateHandle.set("categoryTabList", data)
                    } else {
                        liveData.postValue(null)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    //空数据页面
                    liveData.postValue(null)
                }

            })
        return liveData

    }
}