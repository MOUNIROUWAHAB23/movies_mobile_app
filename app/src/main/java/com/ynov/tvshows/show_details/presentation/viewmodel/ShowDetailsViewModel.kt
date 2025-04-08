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
                // Nouveau formatage adapté aux caractères spéciaux
                val formattedShowName = showName
                    .replace(Regex("[']"), "") // Supprime uniquement les apostrophes
                    .replace(
                        Regex("[^a-zA-Z0-9-]"),
                        "-"
                    ) // Remplace autres caractères spéciaux par -
                    .lowercase()
                    .replace(Regex("-+"), "-") // Supprime les tirets multiples

                // Mapping spécifique pour les séries problématiques
                val apiName = when {
                    formattedShowName.contains("stranger-things") -> "montauk"
                    formattedShowName.contains("daredevil") -> "daredevil"
                    formattedShowName.contains("marvels-agents-of-s-h-i-e-l-d") -> "marvel-s-agents-of-s-h-i-e-l-d" // <-- SOLUTION
                    else -> formattedShowName
                }.let {
                    // Encodage final pour les caractères restants
                    URLEncoder.encode(it, "UTF-8").replace("+", "-") // Convertit les + en -
                }

                Timber.d("Requête API avec : apiName=$apiName")
                val details = getShowDetailsUseCase(apiName)

                _state.value = ShowDetailsState(showDetails = details)

            } catch (e: Exception) {
                Timber.e(e, "Erreur technique")
                _state.value = ShowDetailsState(
                    error = when {
                        e.message?.contains("404") == true -> "Série non trouvée"
                        e.message?.contains("429") == true -> "Limite d'appels atteinte"
                        else -> "Erreur technique : ${e.localizedMessage}" // Message plus générique
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
