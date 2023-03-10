package com.hiroshisasmita.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.hiroshisasmita.core.exception.NotFoundException
import com.hiroshisasmita.core.functional.ErrorApiStateHandler

abstract class BaseFragment<BIND: ViewDataBinding>: Fragment() {

    protected lateinit var binding: BIND
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BIND
    abstract fun setupViews()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    protected fun handleErrorApiState(
        throwable: Throwable,
        onDataNotFound: (error: NotFoundException) -> Unit
    ) {
        ErrorApiStateHandler.handleErrorState(
            activity = requireActivity(),
            lifecycle = lifecycle,
            throwable = throwable,
            onDataNotFound = onDataNotFound
        )
    }
}