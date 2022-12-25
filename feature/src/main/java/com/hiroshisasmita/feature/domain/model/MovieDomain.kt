package com.hiroshisasmita.feature.domain.model

data class MovieDomain(
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
)
