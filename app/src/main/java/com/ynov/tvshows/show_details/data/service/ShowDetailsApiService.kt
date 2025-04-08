package com.ynov.tvshows.show_details.data.service

import com.ynov.tvshows.show_details.data.dto.ShowDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowDetailsApiService {
    @GET("show-details")
    suspend fun getShowDetails(@Query("q") showName: String): ShowDetailsResponse
}
