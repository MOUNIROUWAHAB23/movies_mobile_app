// app/src/main/java/com/ynov/tvshows/show_details/presentation/viewmodel/ShowDetailsViewModel.kt
package com.ynov.tvshows.show_details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynov.tvshows.show_details.domain.model.ShowDetails
import com.ynov.tvshows.show_details.domain.useCase.GetShowDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    private val getShowDetailsUseCase: GetShowDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ShowDetailsState())
    val state: StateFlow<ShowDetailsState> = _state.asStateFlow()

    fun loadShowDetails(showName: String) {
        viewModelScope.launch {
            _state.value = ShowDetailsState(isLoading = true)
            try {
                // Remplacer les espaces par des tirets et mettre en minuscule
                val formattedShowName = showName.replace(" ", "-").lowercase()
                // Encodage de l'URL
                val encodedShowName = URLEncoder.encode(formattedShowName, "UTF-8")

                Timber.d("Loading show details for: $encodedShowName") // Vérification

                val details = getShowDetailsUseCase(encodedShowName)
                _state.value = ShowDetailsState(showDetails = details)

            } catch (e: Exception) {
                Timber.e(e, "Erreur lors du chargement des détails")
                _state.value = ShowDetailsState(
                    error = when {
                        e.message?.contains("HTTP 429") == true -> "Serveur surchargé"
                        e.message?.contains("invalide") == true -> "Données corrompues"
                        e.message?.contains("vide") == true -> "Aucun détail trouvé"
                        else -> "Erreur de chargement"
                    }
                )
            }
        }
    }
}
data class ShowDetailsState(
    val showDetails: ShowDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
