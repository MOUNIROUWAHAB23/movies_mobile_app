package com.ynov.tvshows.most_popular.presentation.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ynov.tvshows.most_popular.presentation.components.MostPopularScreen
import com.ynov.tvshows.show_details.presentation.components.ShowDetailsScreen
import com.ynov.tvshows.ui.theme.TvShowsTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MostPopularActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TvShowsTheme {
                // Créez un NavController pour gérer la navigation entre les écrans
                val navController = rememberNavController()

                // Surface pour appliquer les thèmes Material Design
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Configurer le NavHost pour gérer les différentes destinations
                    NavHost(navController = navController, startDestination = "most_popular") {
                        // Composable pour afficher la liste des séries populaires
                        composable("most_popular") {
                            MostPopularScreen(
                                onTvShowClick = { permalink ->
                                    // Navigation vers l'écran des détails avec le permalink de la série
                                    navController.navigate("show_details/$permalink")
                                }
                            )
                        }

                        // Composable pour afficher les détails d'une série en fonction du permalink
                        composable("show_details/{permalink}") { backStackEntry ->
                            // Récupérer le permalink depuis les arguments de la navigation
                            val permalink = backStackEntry.arguments?.getString("permalink")
                            ShowDetailsScreen(permalink = permalink ?: "")
                        }
                    }
                }
            }
        }
    }
}
