package com.hiroshisasmita.feature.data.service

import com.hiroshisasmita.feature.data.model.response.GenreResponse
import com.hiroshisasmita.feature.data.model.response.MovieItem
import com.hiroshisasmita.feature.data.model.response.MovieVideoResponse
import com.hiroshisasmita.feature.data.model.response.PaginationWrapper
import com.hiroshisasmita.feature.data.model.response.ReviewItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenreResponse>

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): Response<PaginationWrapper<MovieItem>>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int
    ): Response<MovieVideoResponse>

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): Response<PaginationWrapper<ReviewItem>>
}