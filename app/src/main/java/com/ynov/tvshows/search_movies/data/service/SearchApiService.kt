package com.ynov.tvshows.search_movies.data.service

import com.ynov.tvshows.search_movies.data.dto.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("search")
    suspend fun searchShows(
        @Query("q") query: String,
        @Query("page") page: Int = 1
    ): SearchResponse
}
