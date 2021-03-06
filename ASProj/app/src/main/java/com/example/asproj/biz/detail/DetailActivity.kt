package com.example.asproj.biz.detail

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.asproj.R
import com.example.asproj.biz.account.AccountManager
import com.example.asproj.fragment.home.GoodsItem
import com.example.asproj.http.model.DetailModel
import com.example.asproj.http.model.GoodsModel
import com.example.asproj.http.model.selectPrice
import com.example.asproj.rote.HiRoute
import com.example.common.ui.component.HiBaseActivity
import com.example.common.ui.view.EmptyView
import com.example.hi_library.utils.HiStatusBarUtil
import com.example.hi_ui.ui.dataitem.HiAdapter
import com.example.hi_ui.ui.dataitem.HiDataItem
import kotlinx.android.synthetic.main.activity_detail.*

/***
 * @author 栾桂明
 * @date 2020年 1月22日
 * @desc  商品的详情页
 */
@Route(path = "/detail/main")
class DetailActivity : HiBaseActivity() {
    private lateinit var viewModel: DetailViewModel
    private var emptyView: EmptyView? = null

    //商品id
    @JvmField
    @Autowired
    var goodId: String? = null

    @JvmField
    @Autowired
    var goodModel: GoodsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HiStatusBarUtil.setStatusBar(this, true, Color.TRANSPARENT, true)
        //路由框架的参数自动注入
        HiRoute.inject(this)
        //对商品Id添加一个断言  强提醒
        assert(!TextUtils.isEmpty(goodId)) { "goodsId must be not null" }
        setContentView(R.layout.activity_detail)
        initView()
        preBindData()
        viewModel = DetailViewModel.get(goodId, this)
        //获取商品详情数据
        viewModel.queryDetailData().observe(this, Observer {
            if (it == null) {
                showEmptyView()
            } else {
                bindData(it)
            }
        })

    }

    /***
     * 预渲染的数据的绑定
     * kotlin 范型
     * out 参数
     * in 返回值
     */
    private fun preBindData() {
        if (goodModel == null) return
        val hiAdapter = recycler_view.adapter as HiAdapter
        hiAdapter.addItemAt(
            0, HeaderItem(
                goodModel!!.sliderImages,
                selectPrice(goodModel!!.groupPrice, goodModel!!.marketPrice),
                goodModel!!.completedNumText,
                goodModel!!.goodsName
            ), false
        )

    }

    /***
     * 数据加载成功的时候的数据的绑定的操作
     */
    private fun bindData(detailModel: DetailModel) {
        recycler_view.visibility = View.VISIBLE
        emptyView?.visibility = View.GONE
        val hiAdapter = recycler_view.adapter as HiAdapter
        val dataItems = mutableListOf<HiDataItem<*, *>>()
        dataItems.add(
            HeaderItem(
                detailModel.sliderImages,
                selectPrice(detailModel.groupPrice, detailModel.marketPrice),
                detailModel.completedNumText,
                detailModel.goodsName
            )
        )
        dataItems.add(
            CommentItem(detailModel)
        )
        dataItems.add(
            ShopItem(detailModel)
        )
        dataItems.add(
            GoodAttrItem(detailModel)
        )
        detailModel.gallery?.forEach {
            dataItems.add(GalleryItem(it))
        }
        detailModel.similarGoods?.let {
            dataItems.add(SimilarTitleItem())
            it.forEach {
                dataItems.add(GoodsItem(it, false))
            }
        }
        hiAdapter.clearItems()
        hiAdapter.addItems(dataItems, true)
        updateFavoriteActionFace(detailModel.isFavorite)
        updateOrderActionFace(detailModel)

    }
    private fun updateOrderActionFace(detailModel: DetailModel) {
        action_order.text = "${selectPrice(
            detailModel.groupPrice,
            detailModel.marketPrice
        )}" + getString(R.string.detail_order_action)
        //点击  现在先不写
    }


    private fun updateFavoriteActionFace(favorite: Boolean) {
        action_favorite.setOnClickListener {
            toggleFavorite()
        }
        action_favorite.setTextColor(
            ContextCompat.getColor(
                this,
                if (favorite) R.color.color_dd2 else R.color.color_999
            )
        )
    }

    private fun toggleFavorite() {
        if (!AccountManager.isLogin()) {
            AccountManager.login(this, Observer { loginSuccess ->
                if (loginSuccess) {
                    toggleFavorite()
                }
            })
        } else {
            action_favorite.isClickable = false
            viewModel.toggleFavorite().observe(this, Observer { success ->
                if (success != null) {
                    //网络成功
                    action_favorite.isClickable = true
                    updateFavoriteActionFace(success)

                    val message = if (success) getString(R.string.detail_favorite_success) else getString(
                                            R.string.detail_favorite_cancel)
                    showToast(message)
                } else {
                    //网路失败
                    action_favorite.isClickable = true

                }
            })
        }
    }

    /***
     * 数据为空的时候展示的空布局
     */
    private fun showEmptyView() {
        if (emptyView == null) {
            emptyView = EmptyView(this)
            emptyView!!.setIcon(R.string.if_empty3)
            emptyView!!.setDesc(getString(R.string.list_empty_desc))
            emptyView!!.layoutParams = ConstraintLayout.LayoutParams(-1, -1)
            emptyView!!.setBackgroundColor(Color.WHITE)
            emptyView!!.setRefresh(getString(R.string.list_empty_action), View.OnClickListener {
                viewModel.queryDetailData()
            })
            root_container.addView(emptyView)
        }
        recycler_view.visibility = View.GONE
        emptyView!!.visibility = View.VISIBLE
    }

    /***
     * 初始化布局
     */
    private fun initView() {
        action_back.setOnClickListener {
            onBackPressed()
        }
        recycler_view.layoutManager = GridLayoutManager(this, 2)
        recycler_view.addOnScrollListener(TitleScrollListener(callback = {
            title_bar.setBackgroundColor(it)
        }))
        recycler_view.adapter = HiAdapter(this)
    }
    /****
     * 吐司提示
     */
    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    }
}