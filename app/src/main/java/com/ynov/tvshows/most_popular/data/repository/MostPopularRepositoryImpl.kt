package com.ynov.tvshows.most_popular.data.repository

import com.ynov.tvshows.most_popular.data.dto.TvShowDto
import com.ynov.tvshows.most_popular.data.service.MostPopularApiService
import com.ynov.tvshows.most_popular.domain.model.TvShow
import com.ynov.tvshows.most_popular.domain.repository.MostPopularRepository
import javax.inject.Inject

class MostPopularRepositoryImpl @Inject constructor(
    private val apiService: MostPopularApiService
) : MostPopularRepository {

    override suspend fun getMostPopularShows(page: Int): List<TvShow> {
        println("DEBUG - Chargement page $page")
        val response = apiService.getMostPopularShows(page)
        println("DEBUG - Réponse reçue : ${response.tv_shows.size} éléments")
        return response.tv_shows.map {
            println("DEBUG - Conversion de ${it.name}")
            it.toTvShow()
        }
    }
}


private fun TvShowDto.toTvShow(): TvShow {
    return TvShow(
        id = id,
        name = name,
        permalink = permalink,
        startDate = start_date,
        network = network,
        status = status,
        imageUrl = image_thumbnail_path
    )
}
