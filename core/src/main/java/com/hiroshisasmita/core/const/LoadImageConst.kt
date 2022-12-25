package com.hiroshisasmita.core.const

object LoadImageConst {
    private const val BASE_URL_LOAD_IMAGE = "https://image.tmdb.org/t/p/w500/"
    fun buildImageUrl(posterPath: String): String {
        return BASE_URL_LOAD_IMAGE + posterPath
    }
}