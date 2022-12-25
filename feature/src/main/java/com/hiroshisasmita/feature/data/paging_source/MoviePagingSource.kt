package com.hiroshisasmita.feature.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hiroshisasmita.core.const.ServiceConst.INIT_PAGINATION_PAGE
import com.hiroshisasmita.core.functional.ApiHandler.handleApi
import com.hiroshisasmita.feature.data.service.MovieService
import com.hiroshisasmita.feature.domain.model.MovieDomain

class MoviePagingSource(
    private val movieService: MovieService,
    private val genreId: Int
): PagingSource<Int, MovieDomain>() {
    override fun getRefreshKey(state: PagingState<Int, MovieDomain>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDomain> {
        val page = params.key ?: INIT_PAGINATION_PAGE

        return try {
            val result = handleApi {
                movieService.getMovies(genreId, page)
            }
            val totalPages = result?.totalPages ?: INIT_PAGINATION_PAGE

            LoadResult.Page(
                data = result?.results?.map { it.toDomain() } ?: listOf(),
                prevKey = null,
                nextKey = if (page < totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}