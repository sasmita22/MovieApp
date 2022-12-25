package com.hiroshisasmita.feature.domain.model

data class ReviewDomain(
    var author: String,
    var authorDetails: AuthorDomain,
    var content: String,
    var createdAt: String,
    var id: String,
    var updatedAt: String,
    var url: String
)
