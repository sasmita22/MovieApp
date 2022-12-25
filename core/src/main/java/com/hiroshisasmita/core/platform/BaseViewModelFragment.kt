package com.hiroshisasmita.core.platform

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseViewModelFragment<VM: ViewModel, BIND: ViewDataBinding>: BaseFragment<BIND>() {
    abstract val viewModel: VM
    abstract fun setupObservers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }
}