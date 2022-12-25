package com.hiroshisasmita.core.functional

sealed class ResultState<out R> {

    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val exception: Exception) : ResultState<Nothing>()

    fun onSuccess(
        block: (R) -> Unit
    ): ResultState<R> {
        if (this is Success) block.invoke(this.data)
        return this
    }

    fun onError(
        block: (Exception) -> Unit
    ): ResultState<R> {
        if (this is Error) block.invoke(this.exception)
        return this
    }
}

inline fun <T, R> ResultState<T>.map(transform: (T) -> R): ResultState<R> {
    return when (this) {
        is ResultState.Success -> {
            ResultState.Success(transform.invoke(this.data))
        }
        is ResultState.Error -> {
            ResultState.Error(this.exception)
        }
    }
}