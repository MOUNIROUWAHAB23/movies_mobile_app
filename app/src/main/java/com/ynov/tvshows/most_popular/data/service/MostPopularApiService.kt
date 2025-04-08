package com.ynov.tvshows.most_popular.data.service

import com.ynov.tvshows.most_popular.data.dto.MostPopularResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MostPopularApiService {
    @GET("most-popular")
    suspend fun getMostPopularShows(
        @Query("page") page: Int
    ): MostPopularResponse
}
