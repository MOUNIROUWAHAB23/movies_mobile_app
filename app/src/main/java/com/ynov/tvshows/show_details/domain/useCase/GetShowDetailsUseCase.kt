// app/src/main/java/com/ynov/tvshows/show_details/domain/useCase/GetShowDetailsUseCase.kt
package com.ynov.tvshows.show_details.domain.useCase

import com.ynov.tvshows.show_details.domain.model.ShowDetails
import com.ynov.tvshows.show_details.domain.repository.ShowDetailsRepository
import javax.inject.Inject

class GetShowDetailsUseCase @Inject constructor(
    private val repository: ShowDetailsRepository // Utilisation de l'interface
) {
    suspend operator fun invoke(showId: String): ShowDetails {
        return repository.getShowDetails(showId)
    }
}
