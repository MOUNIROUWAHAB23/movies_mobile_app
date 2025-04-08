package com.ynov.tvshows.show_details.di


import com.ynov.tvshows.show_details.data.repository.ShowDetailsRepositoryImpl
import com.ynov.tvshows.show_details.data.service.ShowDetailsApiService
import com.ynov.tvshows.show_details.domain.repository.ShowDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShowDetailsModule {

    @Provides
    @Singleton
    fun provideShowDetailsRepository(
        apiService: ShowDetailsApiService // Injecté depuis NetworkModule
    ): ShowDetailsRepository {
        return ShowDetailsRepositoryImpl(apiService)
    }

    // Supprimer la méthode provideShowDetailsApiService() si existante
}
