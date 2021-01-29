package com.example.hiarch.java_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.hi_arch.arch.java.mvvm.LoginViewModel
import com.example.hiarch.R
import com.example.hiarch.databinding.ActivityMvvmTestBinding
import org.w3c.dom.Text
import kotlin.math.log

class MvvmTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMvvmTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_test)
        val loginViewModel = LoginViewModel()
        binding.loginViewModel = loginViewModel
        loginViewModel.Login()
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                Log.e("tag", loginViewModel.userField.get()!!.gender)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }
}