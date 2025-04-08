package com.ynov.tvshows.most_popular.domain.model

data class TvShow(
    val id: Int,
    val name: String,
    val permalink: String,
    val startDate: String?,
    val network: String?,
    val status: String,
    val imageUrl: String?
)
