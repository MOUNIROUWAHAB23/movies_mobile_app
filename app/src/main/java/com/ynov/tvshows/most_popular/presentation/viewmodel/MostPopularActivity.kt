package com.ynov.tvshows.most_popular.presentation.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ynov.tvshows.most_popular.presentation.components.MostPopularScreen
import com.ynov.tvshows.search_movies.presentation.components.SearchScreen
import com.ynov.tvshows.show_details.presentation.components.ShowDetailsScreen
import com.ynov.tvshows.ui.theme.TvShowsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MostPopularActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TvShowsTheme {
                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = "most_popular"
                    ) {
                        composable("most_popular") {
                            MostPopularScreen(
                                onTvShowClick = { permalink ->
                                    navController.navigate("show_details/$permalink")
                                },
                                onSearchNavigate = {
                                    navController.navigate("search_screen")
                                }
                            )
                        }

                        composable("show_details/{permalink}") { backStackEntry ->
                            val permalink = backStackEntry.arguments?.getString("permalink") ?: ""
                            ShowDetailsScreen(permalink)
                        }

                        composable("search_screen") {
                            SearchScreen(
                                onShowSelected = { permalink ->
                                    navController.navigate("show_details/$permalink")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
