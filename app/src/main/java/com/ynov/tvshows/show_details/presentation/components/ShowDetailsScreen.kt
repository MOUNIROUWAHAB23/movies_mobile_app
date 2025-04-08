package com.ynov.tvshows.show_details.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ynov.tvshows.show_details.domain.model.ShowDetails
import com.ynov.tvshows.show_details.presentation.viewmodel.ShowDetailsViewModel

@Composable
fun ShowDetailsScreen(
    permalink: String,
    viewModel: ShowDetailsViewModel = hiltViewModel()
) {
    // Déclenche le chargement au lancement
    LaunchedEffect(permalink) {
        viewModel.loadShowDetails(permalink)
    }

    val state = viewModel.state.collectAsState().value

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            // État de chargement principal
            state.isLoading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Chargement en cours...")
                }
            }

            // Gestion des erreurs détaillée
            state.error != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Erreur de chargement",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = state.error,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Bouton de réessai (optionnel)
                    Button(onClick = { viewModel.loadShowDetails(permalink) }) {
                        Text("Réessayer")
                    }
                }
            }

            // Affichage des données
            state.showDetails != null -> {
                val show = state.showDetails

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item { HeaderSection(show) }
                    item { DescriptionSection(show) }
                    item { InfoSection(show) }
                    item { EpisodeStatsSection(show) }
                }
            }
        }
    }
}

// Section Image + Titre
@Composable
private fun HeaderSection(show: ShowDetails) {
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

    Text(
        text = show.name,
        style = MaterialTheme.typography.headlineMedium
    )
}

// Section Description
@Composable
private fun DescriptionSection(show: ShowDetails) {
    val description = show.description.takeIf { !it.isNullOrBlank() }
        ?: "Pas de description disponible"

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = description, style = MaterialTheme.typography.bodyLarge)
    }
}

// Section Informations
@Composable
private fun InfoSection(show: ShowDetails) {
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
}

// Section Statistiques Épisodes (MODIFIÉE)
@Composable
private fun EpisodeStatsSection(show: ShowDetails) {
    val episodes = show.episodes ?: emptyList()

    // Calcul des totaux
    val totalSeasons = episodes
        .distinctBy { it.season }
        .maxOfOrNull { it.season } ?: 0

    val totalEpisodes = episodes.size

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Épisodes",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        InfoRow(label = "Saisons", value = totalSeasons.toString())
        InfoRow(label = "Épisodes", value = totalEpisodes.toString())

        // Dernier épisode
        episodes.lastOrNull()?.let { lastEpisode ->
            InfoRow(
                label = "Dernier épisode",
                value = "S${lastEpisode.season}E${lastEpisode.episodeNumber}"
            )
        }
    }
}
