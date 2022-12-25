package com.hiroshisasmita.feature.data.model.response

import com.google.gson.annotations.SerializedName
import com.hiroshisasmita.feature.domain.model.AuthorDomain
import com.hiroshisasmita.feature.domain.model.ReviewDomain

data class ReviewItem(
    @SerializedName("author") var author: String? = null,
    @SerializedName("author_details") var authorDetails: AuthorItem? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("url") var url: String? = null
) {
    fun toDomain(): ReviewDomain {
        return ReviewDomain(
            author = this.author.orEmpty(),
            authorDetails = this.authorDetails?.toDomain() ?: AuthorDomain.createDefault(),
            content = this.content.orEmpty(),
            createdAt = this.createdAt.orEmpty(),
            id = this.id.orEmpty(),
            updatedAt = this.updatedAt.orEmpty(),
            url = this.url.orEmpty()
        )
    }
}
