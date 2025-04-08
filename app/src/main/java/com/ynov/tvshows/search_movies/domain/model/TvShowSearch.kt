package com.ynov.tvshows.search_movies.domain.model

data class TvShowSearch(
    val id: Int,
    val name: String,
    val permalink: String,
    val imageThumbnailPath: String,
    val status: String,
    val network: String,
    val startDate: String?
)
