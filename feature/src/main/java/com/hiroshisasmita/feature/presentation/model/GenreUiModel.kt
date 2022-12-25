package com.hiroshisasmita.feature.presentation.model

import com.hiroshisasmita.feature.domain.model.GenreDomain

data class GenreUiModel(
    val id: Int,
    val name: String
) {
    companion object {
        fun parse(domain: GenreDomain): GenreUiModel {
            return GenreUiModel(
                id = domain.id,
                name = domain.name
            )
        }
    }

    fun toDomain(): GenreDomain {
        return GenreDomain(
            id = this.id,
            name = this.name
        )
    }
}
