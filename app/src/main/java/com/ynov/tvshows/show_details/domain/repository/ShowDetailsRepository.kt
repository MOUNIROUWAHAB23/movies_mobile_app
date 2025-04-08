package com.ynov.tvshows.show_details.domain.repository

import com.ynov.tvshows.show_details.domain.model.ShowDetails

interface ShowDetailsRepository {
    suspend fun getShowDetails(showId: String): ShowDetails
}
