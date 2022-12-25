package com.hiroshisasmita.feature.presentation.model

import com.hiroshisasmita.feature.domain.model.MovieVideoDomain

data class MovieVideoUiModel(
    var iso6391: String,
    var iso31661: String,
    var name: String,
    var key: String,
    var site: String,
    var size: Int,
    var type: String,
    var official: Boolean,
    var publishedAt: String,
    var id: String
) {
    companion object {
        fun parse(domain: MovieVideoDomain): MovieVideoUiModel {
            return MovieVideoUiModel(
                iso6391 = domain.iso6391,
                iso31661 = domain.iso31661,
                name = domain.name,
                key = domain.key,
                site = domain.site,
                size = domain.size,
                type = domain.type,
                official = domain.official,
                publishedAt = domain.publishedAt,
                id = domain.id
            )
        }
    }

    fun toDomain(): MovieVideoDomain {
        return MovieVideoDomain(
            iso6391 = this.iso6391,
            iso31661 = this.iso31661,
            name = this.name,
            key = this.key,
            site = this.site,
            size = this.size,
            type = this.type,
            official = this.official,
            publishedAt = this.publishedAt,
            id = this.id
        )
    }
}
