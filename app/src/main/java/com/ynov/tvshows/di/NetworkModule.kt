package com.ynov.tvshows.di

import com.ynov.tvshows.most_popular.data.service.MostPopularApiService
import com.ynov.tvshows.show_details.data.service.ShowDetailsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val response = chain.proceed(chain.request())
                if (response.code == 429) {
                    throw IOException("HTTP 429 Too Many Requests")
                }
                response
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.episodate.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideShowDetailsApiService(retrofit: Retrofit): ShowDetailsApiService {
        return retrofit.create(ShowDetailsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMostPopularApiService(retrofit: Retrofit): MostPopularApiService {
        return retrofit.create(MostPopularApiService::class.java)
    }
}
