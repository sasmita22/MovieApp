package com.hiroshisasmita.feature.domain.usecase

import androidx.paging.PagingData
import com.hiroshisasmita.core.interactor.UseCase
import com.hiroshisasmita.feature.domain.model.MovieDomain
import com.hiroshisasmita.feature.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMovieUseCase @Inject constructor(
    private val movieRepository: IMovieRepository
): UseCase<FetchMovieUseCase.RequestValues, FetchMovieUseCase.ResponseValues>() {
    class RequestValues(val genreId: Int): UseCase.RequestValues
    class ResponseValues(val result: Flow<PagingData<MovieDomain>>): UseCase.ResponseValues

    override fun execute(requestValues: RequestValues): ResponseValues {
        return ResponseValues(
            movieRepository.getMovies(requestValues.genreId)
        )
    }
}