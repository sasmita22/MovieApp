package com.hiroshisasmita.feature.domain.usecase

import com.hiroshisasmita.core.functional.ResultState
import com.hiroshisasmita.core.interactor.SuspendUseCase
import com.hiroshisasmita.feature.domain.model.GenreDomain
import com.hiroshisasmita.feature.domain.model.MovieVideoDomain
import com.hiroshisasmita.feature.domain.repository.IMovieRepository
import javax.inject.Inject

class FetchMovieVideoUseCase @Inject constructor(
    private val movieRepository: IMovieRepository
): SuspendUseCase<FetchMovieVideoUseCase.RequestValues, FetchMovieVideoUseCase.ResponseValues>() {

    class RequestValues(val movieId: Int): SuspendUseCase.RequestValues
    class ResponseValues(val result: ResultState<MovieVideoDomain?>): SuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val result = movieRepository.getMovieVideo(requestValues.movieId)
            ResponseValues(ResultState.Success(result))
        } catch (e: Exception) {
            ResponseValues(ResultState.Error(e))
        }
    }
}