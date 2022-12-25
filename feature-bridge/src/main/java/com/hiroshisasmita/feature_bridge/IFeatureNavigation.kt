package com.hiroshisasmita.feature_bridge

import android.content.Context
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface IFeatureNavigation {

    fun navigateToGenre(context: Context)
    fun navigateToMovieList(context: Context, genreId: Int, genreName: String)
    fun navigateToMovieDetail(context: Context, movieDataIntent: MovieDataIntent)
}

@Parcelize
data class MovieDataIntent(
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
): Parcelable