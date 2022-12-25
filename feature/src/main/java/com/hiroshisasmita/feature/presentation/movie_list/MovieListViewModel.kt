package com.hiroshisasmita.feature.presentation.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.hiroshisasmita.feature.domain.usecase.FetchMovieUseCase
import com.hiroshisasmita.feature.presentation.model.MovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val fetchMovieUseCase: FetchMovieUseCase
): ViewModel() {

    fun fetchMovies(genreId: Int): Flow<PagingData<MovieUiModel>> {
        return fetchMovieUseCase.execute(FetchMovieUseCase.RequestValues(genreId))
            .result
            .map {
                it.map {
                    MovieUiModel.parse(it)
                }
            }
            .cachedIn(viewModelScope)
    }
}