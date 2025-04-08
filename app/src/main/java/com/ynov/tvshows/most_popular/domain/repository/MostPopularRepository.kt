package com.ynov.tvshows.most_popular.domain.repository

import com.ynov.tvshows.most_popular.domain.model.TvShow

interface MostPopularRepository {
    suspend fun getMostPopularShows(page: Int): List<TvShow>
}
