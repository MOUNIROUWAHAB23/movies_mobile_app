package com.ynov.tvshows.show_details.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ynov.tvshows.show_details.presentation.viewmodel.ShowDetailsViewModel

@Composable
fun ShowDetailsScreen(
    permalink: String,
    viewModel: ShowDetailsViewModel = hiltViewModel()
) {
    viewModel.loadShowDetails(permalink)

    val state = viewModel.state.collectAsState().value

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(
                    text = "Erreur : ${state.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }

            state.showDetails != null -> {
                val show = state.showDetails
                val description = show.description.takeIf { !it.isNullOrBlank() }
                    ?: "Pas de description disponible"

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()), // Ajout du scroll
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    // Section Image
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(show.imageThumbnailPath)
                            .crossfade(true)
                            .build(),
                        contentDescription = show.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Section Titre
                    Text(
                        text = show.name,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Section Description
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Section Informations Principales
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Informations",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        InfoRow(label = "Statut", value = show.status)
                        InfoRow(label = "Date de début", value = show.startDate ?: "Inconnue")
                        InfoRow(label = "Date de fin", value = show.endDate ?: "En cours")
                        InfoRow(label = "Durée", value = "${show.runtime ?: "?"} minutes")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Section Épisodes
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Épisodes",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        val totalSeasons = show.episodes?.maxOfOrNull { it.season } ?: 0
                        val totalEpisodes = show.episodes?.size ?: 0

                        InfoRow(label = "Nombre total de saisons", value = totalSeasons.toString())
                        InfoRow(label = "Nombre total d'épisodes", value = totalEpisodes.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}