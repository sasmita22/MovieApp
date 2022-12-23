package com.hiroshisasmita.movieapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.hiroshisasmita.core.platform.BaseActivity
import com.hiroshisasmita.movieapp.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    override fun setupViews() {

    }
}