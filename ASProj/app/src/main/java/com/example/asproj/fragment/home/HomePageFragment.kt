package com.example.asproj.fragment.home

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asproj.R
import com.example.common.uicomponent.HiBaseFragment
import com.example.hi_dataitem.HiAdapter
import com.example.hi_dataitem.HiDataItem
import java.util.ArrayList

class HomePageFragment : HiBaseFragment() {
    var dataSets: ArrayList<HiDataItem<*, out RecyclerView.ViewHolder>> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HiAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initLayout() {
        recyclerView = layoutView.findViewById(R.id.home_recyclerView)
        adapter = HiAdapter(context!!)
        recyclerView.layoutManager = GridLayoutManager(context!!, 2)
        recyclerView.adapter = adapter
        dataSets.add(HomeTopBarItem(HomeItemData(), context!!))
        adapter.addItems(dataSets, false)


    }

}