package com.ynov.tvshows.search_movies.domain.useCase


import com.ynov.tvshows.search_movies.domain.model.TvShowSearch
import com.ynov.tvshows.search_movies.domain.repository.SearchRepository
import javax.inject.Inject

class SearchShowsUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String): List<TvShowSearch> {
        return repository.searchShows(query)
    }
}
