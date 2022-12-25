package com.hiroshisasmita.feature.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.hiroshisasmita.core.functional.ResultState
import com.hiroshisasmita.core.functional.map
import com.hiroshisasmita.feature.domain.model.ReviewDomain
import com.hiroshisasmita.feature.domain.usecase.FetchMovieVideoUseCase
import com.hiroshisasmita.feature.domain.usecase.FetchReviewUseCase
import com.hiroshisasmita.feature.presentation.model.GenreUiModel
import com.hiroshisasmita.feature.presentation.model.MovieUiModel
import com.hiroshisasmita.feature.presentation.model.MovieVideoUiModel
import com.hiroshisasmita.feature.presentation.model.ReviewUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val fetchMovieVideoUseCase: FetchMovieVideoUseCase,
    private val fetchReviewUseCase: FetchReviewUseCase
) : ViewModel() {
    private val _movieItem by lazy { MutableStateFlow<MovieUiModel?>(null) }
    val movieItem: StateFlow<MovieUiModel?> = _movieItem

    private val _videoResult by lazy { MutableStateFlow<ResultState<MovieVideoUiModel?>>(ResultState.Success(null)) }
    val videoResult: StateFlow<ResultState<MovieVideoUiModel?>> = _videoResult

    fun setMovieData(movieItem: MovieUiModel) {
        _movieItem.value = movieItem
    }

    fun fetchVideos(movieId: Int) {
        viewModelScope.launch {
            fetchMovieVideoUseCase.execute(FetchMovieVideoUseCase.RequestValues(movieId))
                .result
                .map { movieDomain ->
                    movieDomain?.let { MovieVideoUiModel.parse(it) }
                }.let {
                    _videoResult.value = it
                }
        }
    }

    fun fetchReviews(movieId: Int): Flow<PagingData<ReviewUiModel>> {
        return fetchReviewUseCase.execute(FetchReviewUseCase.RequestValues(movieId))
            .result
            .map {
                it.map { reviewDomain -> ReviewUiModel.parse(reviewDomain) }
            }.cachedIn(viewModelScope)
    }
}