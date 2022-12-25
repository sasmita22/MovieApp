package com.hiroshisasmita.feature.domain.usecase

import com.hiroshisasmita.core.functional.ResultState
import com.hiroshisasmita.core.interactor.SuspendUseCase
import com.hiroshisasmita.feature.domain.model.GenreDomain
import com.hiroshisasmita.feature.domain.repository.IMovieRepository
import javax.inject.Inject

class FetchGenresUseCase @Inject constructor(
    private val movieRepository: IMovieRepository
): SuspendUseCase<FetchGenresUseCase.RequestValues, FetchGenresUseCase.ResponseValues>() {

    class RequestValues(): SuspendUseCase.RequestValues
    class ResponseValues(val result: ResultState<List<GenreDomain>>): SuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val result = movieRepository.getGenres()
            ResponseValues(ResultState.Success(result))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}