package com.ynov.tvshows.most_popular.di


import com.ynov.tvshows.most_popular.data.repository.MostPopularRepositoryImpl
import com.ynov.tvshows.most_popular.data.service.MostPopularApiService
import com.ynov.tvshows.most_popular.domain.repository.MostPopularRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MostPopularModule {

    @Provides
    @Singleton
    fun provideMostPopularRepository(
        apiService: MostPopularApiService // Injecté depuis NetworkModule
    ): MostPopularRepository {
        return MostPopularRepositoryImpl(apiService)
    }

    // Supprimer la méthode provideMostPopularApiService() si existante
}
