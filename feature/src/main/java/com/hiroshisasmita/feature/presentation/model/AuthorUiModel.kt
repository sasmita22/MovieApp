package com.hiroshisasmita.feature.presentation.model

import com.hiroshisasmita.feature.domain.model.AuthorDomain

data class AuthorUiModel(
    var name: String,
    var username: String,
    var avatarPath: String,
    var rating: Int
) {
    companion object {
        fun parse(domain: AuthorDomain): AuthorUiModel {
            return AuthorUiModel(
                name = domain.name,
                username = domain.username,
                avatarPath = domain.avatarPath,
                rating = domain.rating
            )
        }
    }

    fun toDomain(): AuthorDomain {
        return AuthorDomain(
            name = this.name,
            username = this.username,
            avatarPath = this.avatarPath,
            rating = this.rating
        )
    }
}
