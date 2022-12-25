package com.hiroshisasmita.feature.data.model.response

import com.google.gson.annotations.SerializedName
import com.hiroshisasmita.feature.domain.model.MovieVideoDomain

data class VideoItem(
    @SerializedName("iso_639_1") var iso6391: String? = null,
    @SerializedName("iso_3166_1") var iso31661: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("key") var key: String? = null,
    @SerializedName("site") var site: String? = null,
    @SerializedName("size") var size: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("official") var official: Boolean? = null,
    @SerializedName("published_at") var publishedAt: String? = null,
    @SerializedName("id") var id: String? = null
) {
    fun toDomain(): MovieVideoDomain {
        return MovieVideoDomain(
            iso6391 = this.iso6391.orEmpty(),
            iso31661 = this.iso31661.orEmpty(),
            name = this.name.orEmpty(),
            key = this.key.orEmpty(),
            site = this.site.orEmpty(),
            size = this.size ?: 0,
            type = this.type.orEmpty(),
            official = this.official ?: false,
            publishedAt = this.publishedAt.orEmpty(),
            id = this.id.orEmpty()
        )
    }
}
