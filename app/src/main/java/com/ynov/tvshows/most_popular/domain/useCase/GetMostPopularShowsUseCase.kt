package com.ynov.tvshows.most_popular.domain.useCase

import com.ynov.tvshows.most_popular.domain.model.TvShow
import com.ynov.tvshows.most_popular.domain.repository.MostPopularRepository
import javax.inject.Inject

class GetMostPopularShowsUseCase @Inject constructor(
    private val repository: MostPopularRepository
) {
    suspend operator fun invoke(page: Int = 1): List<TvShow> {
        return repository.getMostPopularShows(page)
    }
}
