// app/src/main/java/com/ynov/tvshows/search/domain/repository/SearchRepository.kt
package com.ynov.tvshows.search_movies.domain.repository

import com.ynov.tvshows.search_movies.domain.model.TvShowSearch

interface SearchRepository {
    suspend fun searchShows(query: String): List<TvShowSearch>
}
