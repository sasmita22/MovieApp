package com.hiroshisasmita.feature.data.model.response

import com.google.gson.annotations.SerializedName
import com.hiroshisasmita.feature.domain.model.AuthorDomain

data class AuthorItem(
    @SerializedName("name") var name: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("avatar_path") var avatarPath: String? = null,
    @SerializedName("rating") var rating: Int? = null
) {
    fun toDomain(): AuthorDomain {
        return AuthorDomain(
            name = this.name.orEmpty(),
            username = this.username.orEmpty(),
            avatarPath = this.avatarPath.orEmpty(),
            rating = this.rating ?: 0
        )
    }
}
