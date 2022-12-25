package com.hiroshisasmita.feature.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiroshisasmita.core.functional.ResultState
import com.hiroshisasmita.core.functional.map
import com.hiroshisasmita.feature.domain.usecase.FetchGenresUseCase
import com.hiroshisasmita.feature.presentation.model.GenreUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val fetchGenresUseCase: FetchGenresUseCase
): ViewModel() {
    private val _result by lazy { MutableStateFlow<ResultState<List<GenreUiModel>>>(ResultState.Success(listOf())) }
    val result: StateFlow<ResultState<List<GenreUiModel>>> = _result

    private val _loading by lazy { MutableSharedFlow<Boolean>() }
    val loading: SharedFlow<Boolean> = _loading

    init {
        fetchGenres()
    }

    fun fetchGenres() {
        viewModelScope.launch {
            fetchGenresUseCase.execute(FetchGenresUseCase.RequestValues())
                .result
                .map {
                    it.map { genreDomain -> GenreUiModel.parse(genreDomain) }
                }.let {
                    _result.value = it
                }
        }
    }
}