package com.hiroshisasmita.feature.presentation.model

import com.hiroshisasmita.feature.domain.model.ReviewDomain

data class ReviewUiModel(
    var author: String,
    var authorDetails: AuthorUiModel,
    var content: String,
    var createdAt: String,
    var id: String,
    var updatedAt: String,
    var url: String
) {
    companion object {
        fun parse(domain: ReviewDomain): ReviewUiModel {
            return ReviewUiModel(
                author = domain.author,
                authorDetails = AuthorUiModel.parse(domain.authorDetails),
                content = domain.content,
                createdAt = domain.createdAt,
                id = domain.id,
                updatedAt = domain.updatedAt,
                url = domain.url
            )
        }
    }

    fun toDomain(): ReviewDomain {
        return ReviewDomain(
            author = this.author,
            authorDetails = this.authorDetails.toDomain(),
            content = this.content,
            createdAt = this.createdAt,
            id = this.id,
            updatedAt = this.updatedAt,
            url = this.url
        )
    }
}
