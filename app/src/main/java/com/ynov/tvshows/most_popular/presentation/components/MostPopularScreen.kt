package com.ynov.tvshows.most_popular.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ynov.tvshows.most_popular.presentation.viewmodel.MostPopularState
import com.ynov.tvshows.most_popular.presentation.viewmodel.MostPopularViewModel

@Composable
fun MostPopularScreen(
    modifier: Modifier = Modifier,
    viewModel: MostPopularViewModel = hiltViewModel(),
    onTvShowClick: (String) -> Unit,
    onSearchNavigate: () -> Unit,
    state: MostPopularState? = null
) {
    val uiState by viewModel.state.collectAsState()
    val currentState = state ?: uiState

    LaunchedEffect(state == null) {
        if (state == null) {
            viewModel.loadMostPopularShows()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
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
                        items(currentState.shows) { show ->
                            TvShowItem(
                                tvShow = show,
                                onClick = { onTvShowClick(show.name) }
                            )
                        }
                    }
                }
            }
        }

        // Floating Action Button positionné dans la Box parente
        FloatingActionButton(
            onClick = onSearchNavigate,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = "Rechercher")
        }
    }
}

