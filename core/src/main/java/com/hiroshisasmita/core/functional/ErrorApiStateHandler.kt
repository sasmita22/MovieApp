package com.hiroshisasmita.core.functional

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hiroshisasmita.core.exception.NotFoundException
import com.hiroshisasmita.core.exception.ServerErrorException
import com.hiroshisasmita.core.exception.UnauthorizedException
import com.hiroshisasmita.core.extension.extToast
import com.hiroshisasmita.resources.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorApiStateHandler {

    private var job: Job? = null

    fun handleErrorState(
        activity: Activity,
        lifecycle: Lifecycle,
        throwable: Throwable,
        onDataNotFound: (NotFoundException) -> Unit
    ) = with(activity) {
        when (throwable) {
            is UnauthorizedException -> showApiStateErrorDialog(this, lifecycle, getString(R.string.unauthorized_message))
            is UnknownHostException -> showApiStateErrorDialog(this, lifecycle, getString(R.string.no_internet_error_message))
            is SocketTimeoutException -> showApiStateErrorDialog(this, lifecycle, getString(R.string.no_internet_error_message))
            is ServerErrorException -> extToast(getString(R.string.something_went_wrong_try_again_message))
            is NotFoundException -> onDataNotFound.invoke(throwable)
            else -> extToast(getString(R.string.something_went_wrong_try_again_message))
        }
    }

    private fun showApiStateErrorDialog(activity: Activity, lifecycle: Lifecycle, message: String) =  with(activity) {
        lifecycle.coroutineScope.launch {
            job?.cancel()
            job = launch {
                delay(200)
                MaterialAlertDialogBuilder(this@with)
                    .setMessage(message)
                    .setPositiveButton(resources.getString(R.string.label_ok)) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
        }
    }
}