// data/repository/ShowDetailsRepositoryImpl.kt
package com.ynov.tvshows.show_details.data.repository

import com.ynov.tvshows.show_details.data.dto.TvShowDetailsDto
import com.ynov.tvshows.show_details.data.service.ShowDetailsApiService
import com.ynov.tvshows.show_details.domain.model.ShowDetails
import com.ynov.tvshows.show_details.domain.repository.ShowDetailsRepository
import kotlinx.coroutines.delay
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class ShowDetailsRepositoryImpl @Inject constructor(
    private val apiService: ShowDetailsApiService
) : ShowDetailsRepository {

    override suspend fun getShowDetails(showId: String): ShowDetails {
        var retryCount = 0
        val maxRetries = 3
        var delayMillis = 1000L

        while (retryCount < maxRetries) {
            try {
                val response = apiService.getShowDetails(showId)

                // Log la réponse brute pour déboguer
                Timber.d("API Response Raw: ${response}")

                return response.tvShow?.toShowDetails()
                    ?: throw IllegalStateException("Réponse API invalide : tvShow manquant ou null")

            } catch (e: Exception) {
                when {
                    e.message?.contains("HTTP 429") == true -> {
                        retryCount++
                        Timber.w("Erreur 429 détectée - Tentative $retryCount/$maxRetries")
                        delay(delayMillis)
                        delayMillis *= 2
                    }
                    else -> {
                        Timber.e(e, "Erreur lors de la récupération des données")
                        throw e
                    }
                }
            }
        }
        throw IOException("Trop de requêtes - Veuillez réessayer plus tard")
    }

    private fun TvShowDetailsDto.toShowDetails(): ShowDetails { // ✅ Mapper pour TvShowDetailsDto
        return ShowDetails(
            id = id,
            name = name,
            permalink = permalink,
            url = url,
            description = description,
            descriptionSource = descriptionSource,
            startDate = startDate,
            endDate = endDate,
            country = country,
            status = status,
            runtime = runtime,
            network = network,
            youtubeLink = youtubeLink,
            imagePath = imagePath,
            imageThumbnailPath = imageThumbnailPath,
            rating = rating,
            ratingCount = ratingCount,
            genres = genres,
            pictures = pictures,
            episodes = episodes?.map { it.toEpisode() }
        )
    }

    private fun com.ynov.tvshows.show_details.data.dto.EpisodeDto.toEpisode(): com.ynov.tvshows.show_details.domain.model.Episode {
        return com.ynov.tvshows.show_details.domain.model.Episode(
            season = season,
            episodeNumber = episode,
            title = name,
            airDate = airDate
        )
    }
}
