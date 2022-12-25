package com.hiroshisasmita.feature.data.model.response

import com.google.gson.annotations.SerializedName

data class MovieVideoResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("results")
    val result: List<VideoItem>? = null
)
