package com.ynov.tvshows.search_movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynov.tvshows.search_movies.domain.model.TvShowSearch
import com.ynov.tvshows.search_movies.domain.useCase.SearchShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchShowsUseCase: SearchShowsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    fun searchShows(query: String) {
        viewModelScope.launch {
            _state.value = SearchState(isLoading = true)
            try {
                val results = searchShowsUseCase(query)
                _state.value = SearchState(
                    results = results,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = SearchState(
                    error = e.message ?: "Erreur lors de la recherche",
                    isLoading = false
                )
            }
        }
    }
}

data class SearchState(
    val results: List<TvShowSearch> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
