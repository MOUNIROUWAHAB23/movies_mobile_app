package com.ynov.tvshows.search_movies.data.repository

import com.ynov.tvshows.search_movies.data.dto.TvShowSearchDto
import com.ynov.tvshows.search_movies.data.service.SearchApiService
import com.ynov.tvshows.search_movies.domain.model.TvShowSearch
import com.ynov.tvshows.search_movies.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: SearchApiService
) : SearchRepository {

    override suspend fun searchShows(query: String): List<TvShowSearch> {
        return apiService.searchShows(query).tvShows.map { it.toDomainModel() }
    }

    private fun TvShowSearchDto.toDomainModel(): TvShowSearch {
        return TvShowSearch(
            id = id,
            name = name,
            permalink = permalink,
            imageThumbnailPath = imageThumbnailPath,
            status = status,
            network = network,
            startDate = startDate
        )
    }
}
