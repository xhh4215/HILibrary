package com.example.asproj.http.model

data class HomeModel (
    val bannerList:List<HomeBanner>?,
    val subcategoryList: List<Subcategory>?,
val goodsList: List<GoodsModel>?)

data class TabCategory(val categoryId:String,val categoryName:String,val goodsCount:String)

data class HomeBanner(val cover:String,val createTime:String,val id:String,val sticky:Int,
      val  subtitle:String,val title:String,val type:String,val url:String){
    companion object{
        const val TYPE_GOODS ="goods"
        const val TYPE_RECOMMEND ="recommend"
    }
}

data class Subcategory(val categoryId: String, val groupName:Any,val showType:String,
    val subcategoryIcon:String,val subcategoryId:String,val subcategoryName:String)


data class GoodsModel(
    val categoryId: String,
    val completedNumText:String,
    val createTime: String,
    val goodsId:String,
    val goodsName:String,
    val groupPrice:String,
    val hot:Boolean,
    val joinedAvatars:Any,
    val marketPrice:String,
    val sliderImage:String,
    val sliderImages:List<SliderImage>,
    val tags:String
)

data class SliderImage(val type:Int,val url:String)