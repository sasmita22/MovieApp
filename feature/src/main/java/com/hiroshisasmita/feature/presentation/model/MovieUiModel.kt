package com.hiroshisasmita.feature.presentation.model

import com.hiroshisasmita.feature.domain.model.MovieDomain
import com.hiroshisasmita.feature_bridge.MovieDataIntent

data class MovieUiModel(
    var posterPath: String,
    var adult: Boolean,
    var overview: String,
    var releaseDate: String,
    var genreIds: List<Int>,
    var id: Int,
    var originalTitle: String,
    var originalLanguage: String,
    var title: String,
    var backdropPath: String,
    var popularity: Double,
    var voteCount: Int,
    var video: Boolean,
    var voteAverage: Double
) {
    companion object {
        fun parse(domain: MovieDomain): MovieUiModel {
            return MovieUiModel(
                posterPath = domain.posterPath,
                adult = domain.adult,
                overview = domain.overview,
                releaseDate = domain.releaseDate,
                genreIds = domain.genreIds,
                id = domain.id,
                originalTitle = domain.originalTitle,
                originalLanguage = domain.originalLanguage,
                title = domain.title,
                backdropPath = domain.backdropPath,
                popularity = domain.popularity,
                voteCount = domain.voteCount,
                video = domain.video,
                voteAverage = domain.voteAverage
            )
        }

        fun parse(intentData: MovieDataIntent): MovieUiModel {
            return MovieUiModel(
                posterPath = intentData.posterPath,
                adult = intentData.adult,
                overview = intentData.overview,
                releaseDate = intentData.releaseDate,
                genreIds = intentData.genreIds,
                id = intentData.id,
                originalTitle = intentData.originalTitle,
                originalLanguage = intentData.originalLanguage,
                title = intentData.title,
                backdropPath = intentData.backdropPath,
                popularity = intentData.popularity,
                voteCount = intentData.voteCount,
                video = intentData.video,
                voteAverage = intentData.voteAverage
            )
        }
    }

    fun toDataIntent(): MovieDataIntent {
        return MovieDataIntent(
            posterPath = this.posterPath,
            adult = this.adult,
            overview = this.overview,
            releaseDate = this.releaseDate,
            genreIds = this.genreIds,
            id = this.id,
            originalTitle = this.originalTitle,
            originalLanguage = this.originalLanguage,
            title = this.title,
            backdropPath = this.backdropPath,
            popularity = this.popularity,
            voteCount = this.voteCount,
            video = this.video,
            voteAverage = this.voteAverage
        )
    }
}
