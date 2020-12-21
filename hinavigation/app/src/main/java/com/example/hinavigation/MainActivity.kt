package com.example.hinavigation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.hinavigation.utils.NavUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        // Passing each menu ID as a set of Ids because each
        NavUtil.builderNavGraph(this,hostFragment!!.childFragmentManager,navController, R.id.nav_host_fragment)
        NavUtil.builderBottomBar(navView)
        navView.setOnNavigationItemSelectedListener { item ->
            navController.navigate(item.itemId)
            true
        }
    }
}