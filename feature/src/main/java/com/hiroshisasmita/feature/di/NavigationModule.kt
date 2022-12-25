package com.hiroshisasmita.feature.di

import com.hiroshisasmita.feature.presentation.navigation.FeatureNavigation
import com.hiroshisasmita.feature_bridge.IFeatureNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun providesNavigation(): IFeatureNavigation {
        return FeatureNavigation()
    }
}