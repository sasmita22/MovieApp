package com.hiroshisasmita.feature.domain.repository

import androidx.paging.PagingData
import com.hiroshisasmita.feature.domain.model.GenreDomain
import com.hiroshisasmita.feature.domain.model.MovieDomain
import com.hiroshisasmita.feature.domain.model.MovieVideoDomain
import com.hiroshisasmita.feature.domain.model.ReviewDomain
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    suspend fun getGenres(): List<GenreDomain>
    fun getMovies(genreId: Int): Flow<PagingData<MovieDomain>>
    suspend fun getMovieVideo(movieId: Int): MovieVideoDomain?
    fun getReviews(movieId: Int): Flow<PagingData<ReviewDomain>>
}