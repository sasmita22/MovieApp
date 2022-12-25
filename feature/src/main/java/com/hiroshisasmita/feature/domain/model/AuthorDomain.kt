package com.hiroshisasmita.feature.domain.model

data class AuthorDomain(
    var name: String,
    var username: String,
    var avatarPath: String,
    var rating: Int
) {
    companion object {
        fun createDefault(): AuthorDomain {
            return AuthorDomain("", "", "", 0)
        }
    }
}
