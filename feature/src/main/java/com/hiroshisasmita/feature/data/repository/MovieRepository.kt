package com.hiroshisasmita.feature.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hiroshisasmita.core.functional.ApiHandler
import com.hiroshisasmita.core.functional.ApiHandler.handleApi
import com.hiroshisasmita.feature.data.paging_source.MoviePagingSource
import com.hiroshisasmita.feature.data.paging_source.ReviewPagingSource
import com.hiroshisasmita.feature.data.service.MovieService
import com.hiroshisasmita.feature.domain.model.GenreDomain
import com.hiroshisasmita.feature.domain.model.MovieDomain
import com.hiroshisasmita.feature.domain.model.MovieVideoDomain
import com.hiroshisasmita.feature.domain.model.ReviewDomain
import com.hiroshisasmita.feature.domain.repository.IMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MovieRepository(
    private val movieService: MovieService
): IMovieRepository {
    override suspend fun getGenres(): List<GenreDomain> = withContext(Dispatchers.IO) {
        return@withContext handleApi {
            movieService.getGenres()
        }?.genres?.let {
            it.map { genreItem ->
                genreItem.toDomain()
            }
        } ?: listOf()
    }

    override fun getMovies(genreId: Int): Flow<PagingData<MovieDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieService, genreId)
            }
        ).flow
    }

    override suspend fun getMovieVideo(movieId: Int): MovieVideoDomain? = withContext(Dispatchers.IO) {
        return@withContext handleApi {
            movieService.getMovieVideos(movieId)
        }.let { movieResponse ->
            movieResponse?.result?.firstOrNull {
                it.site.equals("Youtube", true)
                        && (it.type.equals("Teaser", true)
                        || it.type.equals("Trailer", true))
            }?.toDomain()
        }
    }

    override fun getReviews(movieId: Int): Flow<PagingData<ReviewDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ReviewPagingSource(movieService, movieId)
            }
        ).flow
    }

}