package com.hiroshisasmita.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<BIND: ViewDataBinding>: AppCompatActivity() {

    protected lateinit var binding: BIND
    abstract val bindingInflater: (LayoutInflater) -> BIND

    protected abstract fun setupViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(LayoutInflater.from(this))
        setContentView(binding.root)
        setupViews()
    }
}