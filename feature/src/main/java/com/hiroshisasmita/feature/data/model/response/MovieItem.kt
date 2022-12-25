package com.hiroshisasmita.feature.data.model.response

import com.google.gson.annotations.SerializedName
import com.hiroshisasmita.feature.domain.model.MovieDomain

data class MovieItem(
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("genre_ids") var genreIds: List<Int>? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null,
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null
) {
    fun toDomain(): MovieDomain {
        return MovieDomain(
            posterPath = this.posterPath.orEmpty(),
            adult = this.adult ?: false,
            overview = this.overview.orEmpty(),
            releaseDate = releaseDate.orEmpty(),
            genreIds = this.genreIds ?: listOf(),
            id = this.id ?: -1,
            originalTitle = this.originalTitle.orEmpty(),
            originalLanguage = this.originalLanguage.orEmpty(),
            title = this.title.orEmpty(),
            backdropPath = this.backdropPath.orEmpty(),
            popularity = this.popularity ?: 0.0,
            voteCount = this.voteCount ?: 0,
            video = this.video ?: false,
            voteAverage = this.voteAverage ?: 0.0
        )
    }
}
