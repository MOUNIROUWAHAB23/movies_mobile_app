package com.ynov.tvshows.most_popular.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ynov.tvshows.most_popular.domain.model.TvShow
import com.ynov.tvshows.most_popular.presentation.viewmodel.MostPopularViewModel
import com.ynov.tvshows.most_popular.presentation.viewmodel.MostPopularState

@Composable
fun MostPopularScreen(
    modifier: Modifier = Modifier,
    viewModel: MostPopularViewModel = hiltViewModel(),
    onTvShowClick: (String) -> Unit,
    // Nouveau paramètre pour les previews
    state: MostPopularState? = null
) {
    // Priorité au state passé en paramètre
    val uiState by viewModel.state.collectAsState()
    val currentState = state ?: uiState

    LaunchedEffect(state == null) { // Ne charge pas si en mode preview
        if (state == null) {
            viewModel.loadMostPopularShows()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Séries TV populaires",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        when {
            currentState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            currentState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Erreur : ${currentState.error}")
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(currentState.shows) { show -> // Utilisez la liste directement
                        TvShowItem(
                            tvShow = show,
                            onClick = { onTvShowClick(show.name) }
                        )
                    }
                }
            }
        }
    }
}
