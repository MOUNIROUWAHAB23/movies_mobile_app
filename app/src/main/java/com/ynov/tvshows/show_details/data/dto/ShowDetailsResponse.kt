// data/dto/ShowDetailsResponse.kt
package com.ynov.tvshows.show_details.data.dto

import com.google.gson.annotations.SerializedName

data class ShowDetailsResponse(
    @SerializedName("tvShow")
    val tvShow: TvShowDetailsDto? // ✅ tvShow est un objet JSON, pas un tableau
)

data class TvShowDetailsDto(
    val id: Int,
    val name: String,
    val permalink: String,
    val url: String,
    val description: String,
    @SerializedName("description_source")
    val descriptionSource: String?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    val country: String?,
    val status: String,
    val runtime: Int?,
    val network: String?,
    @SerializedName("youtube_link")
    val youtubeLink: String?,
    @SerializedName("image_path")
    val imagePath: String?,
    @SerializedName("image_thumbnail_path")
    val imageThumbnailPath: String?,
    val rating: String?,
    @SerializedName("rating_count")
    val ratingCount: String?,
    val genres: List<String>?,
    val pictures: List<String>?,
    val episodes: List<EpisodeDto>?
)

data class EpisodeDto(
    val season: Int,
    val episode: Int,
    val name: String,
    @SerializedName("air_date")
    val airDate: String
)
