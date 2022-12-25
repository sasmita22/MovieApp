package com.hiroshisasmita.core.interactor

abstract class SuspendUseCase<Q : SuspendUseCase.RequestValues, P : SuspendUseCase.ResponseValues> {

    abstract suspend fun execute(requestValues: Q): P

    /**
     * Data passed to a request.
     */
    interface RequestValues

    /**
     * Data received from a request.
     */
    interface ResponseValues
}