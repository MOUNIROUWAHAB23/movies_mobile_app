package com.ynov.tvshows.most_popular.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynov.tvshows.most_popular.domain.model.TvShow
import com.ynov.tvshows.most_popular.domain.useCase.GetMostPopularShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostPopularViewModel @Inject constructor(
    private val getMostPopularShowsUseCase: GetMostPopularShowsUseCase
) : ViewModel() {

    // Déclaration du State
    private val _state = MutableStateFlow(MostPopularState())
    val state: StateFlow<MostPopularState> = _state.asStateFlow()

    init {
        loadMostPopularShows()
    }

    fun loadMostPopularShows() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val shows = getMostPopularShowsUseCase()
                _state.value = MostPopularState(shows = shows)
            } catch (e: Exception) {
                _state.value = MostPopularState(
                    error = e.message ?: "Erreur inconnue",
                    shows = _state.value.shows
                )
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}

// Ajouter cette déclaration dans le même fichier
data class MostPopularState(
    val shows: List<TvShow> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
