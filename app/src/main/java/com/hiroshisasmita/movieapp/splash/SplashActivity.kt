package com.hiroshisasmita.movieapp.splash

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.hiroshisasmita.core.platform.BaseActivity
import com.hiroshisasmita.feature_bridge.IFeatureNavigation
import com.hiroshisasmita.movieapp.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    @Inject
    lateinit var featureNavigation: IFeatureNavigation

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    override fun setupViews() {
        lifecycleScope.launch {
            delay(2000)
            featureNavigation.navigateToGenre(this@SplashActivity)
        }
    }
}