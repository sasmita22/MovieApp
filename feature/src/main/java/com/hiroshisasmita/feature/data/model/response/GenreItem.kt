package com.hiroshisasmita.feature.data.model.response

import com.google.gson.annotations.SerializedName
import com.hiroshisasmita.feature.domain.model.GenreDomain

data class GenreItem(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
) {
    fun toDomain(): GenreDomain {
        return GenreDomain(
            id = this.id ?: -1,
            name = this.name.orEmpty()
        )
    }
}
