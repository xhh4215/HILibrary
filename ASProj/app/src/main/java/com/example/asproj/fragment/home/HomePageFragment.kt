package com.example.asproj.fragment.home

import android.app.ProgressDialog.show
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.example.asproj.R
import com.example.common.ui.component.HiBaseFragment
import com.example.hi_dataitem.HiAdapter
import com.example.hi_dataitem.HiDataItem
import com.google.android.material.button.MaterialButton
import java.util.ArrayList

class HomePageFragment() : HiBaseFragment() {

     override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutView.findViewById<MaterialButton>(R.id.detail).setOnClickListener {
            navigation("/profile/detail")
        }
        layoutView.findViewById<MaterialButton>(R.id.vip).setOnClickListener {
            navigation("/profile/authentication")
        }
        layoutView.findViewById<MaterialButton>(R.id.account).setOnClickListener {
            navigation("/profile/vip")
        }
        layoutView.findViewById<MaterialButton>(R.id.global).setOnClickListener {
            navigation("/profile/unknow")
        }


    }

    private fun navigation(path: String) {
        ARouter.getInstance().build(path).navigation()
    }




}