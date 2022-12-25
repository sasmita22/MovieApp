package com.hiroshisasmita.core.functional

import com.hiroshisasmita.core.const.ResponseCode
import com.hiroshisasmita.core.exception.NotFoundException
import com.hiroshisasmita.core.exception.ServerErrorException
import com.hiroshisasmita.core.exception.UnauthorizedException
import retrofit2.Response

object ApiHandler {
    suspend fun <T: Any> handleApi(block: suspend () -> Response<T>): T? {
        try {
            val response = block.invoke()

            if (response.isSuccessful) {
                return response.body()
            }

            // When Error
            throw fetchError(response.code())
        } catch (e: Exception) {
            throw e
        }
    }

    private fun fetchError(responseCode: Int): Exception {
        return try {
            val exception = when (responseCode) {
                ResponseCode.UNAUTHORIZED -> UnauthorizedException()
                ResponseCode.NOT_FOUND -> NotFoundException()
                in 500..599 -> ServerErrorException()
                else -> Exception()
            }
            exception
        } catch (exception: Exception) {
            exception
        }
    }
}