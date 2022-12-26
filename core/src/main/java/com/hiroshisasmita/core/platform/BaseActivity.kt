package com.hiroshisasmita.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.hiroshisasmita.core.exception.NotFoundException
import com.hiroshisasmita.core.functional.ErrorApiStateHandler
import java.lang.Exception

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

    protected fun handleErrorApiState(
        throwable: Throwable,
        onDataNotFound: (error: NotFoundException) -> Unit
    ) {
        ErrorApiStateHandler.handleErrorState(
            activity = this,
            lifecycle = lifecycle,
            throwable = throwable,
            onDataNotFound = onDataNotFound
        )
    }
}