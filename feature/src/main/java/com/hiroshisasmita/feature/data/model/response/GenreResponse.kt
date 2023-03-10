package com.hiroshisasmita.feature.data.model.response

import com.google.gson.annotations.SerializedName
import com.hiroshisasmita.feature.domain.model.GenreDomain

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<GenreItem>? = null
)
