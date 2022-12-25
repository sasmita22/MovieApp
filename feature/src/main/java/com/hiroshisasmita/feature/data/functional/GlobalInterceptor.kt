package com.hiroshisasmita.feature.data.functional

import com.hiroshisasmita.feature.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class GlobalInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("language", "en-US")
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}