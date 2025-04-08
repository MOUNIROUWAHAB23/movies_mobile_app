package com.ynov.tvshows.search_movies.di

import com.ynov.tvshows.search_movies.data.repository.SearchRepositoryImpl
import com.ynov.tvshows.search_movies.data.service.SearchApiService
import com.ynov.tvshows.search_movies.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchApiService(retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }

    @Provides
    fun provideSearchRepository(
        apiService: SearchApiService
    ): SearchRepository {
        return SearchRepositoryImpl(apiService)
    }
}
