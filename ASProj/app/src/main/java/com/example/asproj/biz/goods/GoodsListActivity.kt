package com.example.asproj.biz.goods

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.asproj.R
import com.example.asproj.rote.HiRoute
import com.example.common.ui.component.HiBaseActivity
import com.example.hi_library.utils.HiStatusBarUtil
import kotlinx.android.synthetic.main.activity_goods_list.*

/***
 * @date 2020年1月12日
 * @description 商品列表界面  内部存放了一个显示数据的Fragment
 * @author 栾桂明
 */
@Route(path = "/goods/list")
class GoodsListActivity : HiBaseActivity() {
    /****
     * 通过注解   @JvmField @Autowired 实现通过 Aroute实现参数的自动注入
     */
    @JvmField
    @Autowired
    var subcategoryId: String = ""

    @JvmField
    @Autowired
    var categoryId: String = ""

    @JvmField
    @Autowired
    var categoryTitle: String = ""

    //存放Fragment的标识tag
    private var GOODS_LIST_TAG = "goods_list_tag"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HiStatusBarUtil.setStatusBar(this, true, translucent = false)
        setContentView(R.layout.activity_goods_list)
        HiRoute.inject(this)
        action_back.setOnClickListener {
            onBackPressed()
        }
        category_title.text = categoryTitle
        //判断是不是已经存放过Fragment 在当前界面了
        var fragment = supportFragmentManager.findFragmentByTag(GOODS_LIST_TAG)
        //如果当前界面没有存放过Fragment，则创建Fragment
        if (fragment == null) {
            fragment = GoodsListFragment.newInstance(categoryId, subcategoryId)
        }
        // Fragment的事物管理对象
        val transaction = supportFragmentManager.beginTransaction()
        if (!fragment.isAdded) {
            // 添加Fragment同时添加tag
            transaction.add(R.id.category_container, fragment, GOODS_LIST_TAG)
            transaction.show(fragment).commitAllowingStateLoss()

        }
    }
}