package com.ynov.tvshows.show_details.domain.model

data class ShowDetails(
    val id: Int,
    val name: String,
    val permalink: String,
    val url: String,
    val description: String,
    val descriptionSource: String?,
    val startDate: String?,
    val endDate: String?,
    val country: String?,
    val status: String,
    val runtime: Int?,
    val network: String?,
    val youtubeLink: String?,
    val imagePath: String?,
    val imageThumbnailPath: String?,
    val rating: String?,
    val ratingCount: String?,
    val genres: List<String>?,
    val pictures: List<String>?,
    val episodes: List<Episode>?
)

data class Episode(
    val season: Int,
    val episodeNumber: Int,
    val title: String,
    val airDate: String
)
