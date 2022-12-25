package com.hiroshisasmita.feature.di

import com.hiroshisasmita.feature.data.repository.MovieRepository
import com.hiroshisasmita.feature.data.service.MovieService
import com.hiroshisasmita.feature.domain.repository.IMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesMovieRepository(
        movieService: MovieService
    ): IMovieRepository {
        return MovieRepository(movieService)
    }
}