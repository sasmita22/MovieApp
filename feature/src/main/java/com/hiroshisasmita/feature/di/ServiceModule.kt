package com.hiroshisasmita.feature.di

import com.hiroshisasmita.feature.data.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesMovieService(
        retrofit: Retrofit
    ): MovieService {
        return retrofit.create(MovieService::class.java)
    }
}