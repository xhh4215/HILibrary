package com.example.hiarch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.hiarch.databinding.ActivityMainBinding
import com.example.hiarch.java_mvp.MvpInJavaActivity
import com.example.hiarch.java_mvvm.MvvmTestActivity
import com.example.hiarch.kotlin_mvp.MvpInKotlinActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.one.setOnClickListener {
            Intent(this@MainActivity, MvpInJavaActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.two.setOnClickListener {
            Intent(this@MainActivity, MvpInKotlinActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.three.setOnClickListener {
            Intent(this@MainActivity, MvvmTestActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}