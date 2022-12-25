package com.hiroshisasmita.feature.presentation.navigation

import android.content.Context
import com.hiroshisasmita.feature.presentation.detail.MovieDetailActivity
import com.hiroshisasmita.feature.presentation.genre.GenreActivity
import com.hiroshisasmita.feature.presentation.movie_list.MovieListActivity
import com.hiroshisasmita.feature_bridge.IFeatureNavigation
import com.hiroshisasmita.feature_bridge.MovieDataIntent

class FeatureNavigation: IFeatureNavigation {
    override fun navigateToGenre(context: Context) {
        GenreActivity.navigate(context)
    }

    override fun navigateToMovieList(context: Context, genreId: Int, genreName: String) {
        MovieListActivity.navigate(context, genreId, genreName)
    }

    override fun navigateToMovieDetail(context: Context, movieDataIntent: MovieDataIntent) {
        MovieDetailActivity.navigate(context, movieDataIntent)
    }
}