package com.hiroshisasmita.core.extension

import android.content.Context
import android.widget.Toast

fun Context.extToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}