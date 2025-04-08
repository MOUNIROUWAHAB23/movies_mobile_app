package com.ynov.tvshows.search_movies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.ynov.tvshows.search_movies.domain.model.TvShowSearch
import com.ynov.tvshows.search_movies.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    onShowSelected: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val state = viewModel.state.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        // Barre de recherche
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchShows(it)
            },
            label = { Text("Rechercher une série") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            state.error != null -> {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.results) { show ->
                        SearchResultItem(
                            show = show,
                            onItemClick = { onShowSelected(show.permalink) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(
    show: TvShowSearch,
    onItemClick: () -> Unit
) {
    Card(
        onClick = onItemClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = show.imageThumbnailPath,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Column {
                Text(
                    text = show.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Statut : ${show.status}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Network : ${show.network}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
