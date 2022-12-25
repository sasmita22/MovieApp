package com.hiroshisasmita.feature.domain.usecase

import androidx.paging.PagingData
import com.hiroshisasmita.core.interactor.UseCase
import com.hiroshisasmita.feature.domain.model.ReviewDomain
import com.hiroshisasmita.feature.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchReviewUseCase @Inject constructor(
    private val movieRepository: IMovieRepository
): UseCase<FetchReviewUseCase.RequestValues, FetchReviewUseCase.ResponseValues>() {
    class RequestValues(val movieId: Int): UseCase.RequestValues
    class ResponseValues(val result: Flow<PagingData<ReviewDomain>>): UseCase.ResponseValues

    override fun execute(requestValues: RequestValues): ResponseValues {
        return ResponseValues(
            movieRepository.getReviews(requestValues.movieId)
        )
    }
}