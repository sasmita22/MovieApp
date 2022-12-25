package com.hiroshisasmita.feature.data.model.response

import com.google.gson.annotations.SerializedName

data class PaginationWrapper<T: Any>(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("results")
    val results: List<T>? = null
)
