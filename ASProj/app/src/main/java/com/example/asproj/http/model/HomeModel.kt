package com.example.asproj.http.model

import android.os.Parcelable
import android.text.TextUtils
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class HomeModel(
    val bannerList: List<HomeBanner>?,
    val subcategoryList: List<Subcategory>?,
    val goodsList: List<GoodsModel>?
) : Serializable

data class TabCategory(val categoryId: String, val categoryName: String, val goodsCount: String) :
    Serializable

data class HomeBanner(
    val cover: String, val createTime: String, val id: String, val sticky: Int,
    val subtitle: String, val title: String, val type: String, val url: String
) : Serializable {
    companion object {
        const val TYPE_GOODS = "goods"
        const val TYPE_RECOMMEND = "recommend"
    }
}

data class Subcategory(
    val categoryId: String, val groupName: String, val showType: String,
    val subcategoryIcon: String, val subcategoryId: String, val subcategoryName: String
) : Serializable

@Parcelize
data class GoodsModel(
    val categoryId: String,
    val completedNumText: String,
    val createTime: String,
    val goodsId: String,
    val goodsName: String,
    val groupPrice: String,
    val hot: Boolean,
    val joinedAvatars: List<SliderImage>?,
    val marketPrice: String,
    val sliderImage: String,
    val sliderImages: List<SliderImage>?,
    val tags: String
) : Serializable,Parcelable



data class GoodsList(val total: Int, val list: List<GoodsModel>) : Serializable
@Parcelize
data class SliderImage(val type: Int, val url: String) : Serializable,Parcelable

fun selectPrice(groupPrice: String?, marketPrice: String?): String? {
    var price: String? = if (TextUtils.isEmpty(marketPrice)) groupPrice else marketPrice
    if (price?.startsWith("¥") != true) {
        price = "¥".plus(price)
    }
    return price
}