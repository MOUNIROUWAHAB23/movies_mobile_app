package com.ynov.tvshows.search_movies.data.dto

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("tv_shows") val tvShows: List<TvShowSearchDto>,
    val page: Int,
    val pages: Int,
    val total: Int
)

data class TvShowSearchDto(
    val id: Int,
    val name: String,
    val permalink: String,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("end_date") val endDate: String?,
    val country: String,
    val network: String,
    val status: String,
    @SerializedName("image_thumbnail_path") val imageThumbnailPath: String
)
