package com.christopoulos.moviesearch.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.christopoulos.moviesearch.presentation.ui.movie_details.MovieDetailsScreen
import com.christopoulos.moviesearch.presentation.ui.movies_list.MoviesListScreen
import com.christopoulos.moviesearch.presentation.ui.splash.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Destination.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        splashGraph(navController)
        moviesList(navController)
        detailsGraph(navController)
    }
}

private fun NavGraphBuilder.splashGraph(navController: NavController) {
    composable(Destination.Splash.route) {
        SplashScreen(onTimeout = { navController.navigateToSearch() })
    }
}

private fun NavGraphBuilder.moviesList(navController: NavController) {
    composable(Destination.Search.route) {
        MoviesListScreen(
            onMovieClick = { movieId ->
                navController.navigate(Destination.detailsRoute(movieId))
            }
        )
    }
}

private fun NavGraphBuilder.detailsGraph(navController: NavController) {
    composable(Destination.Details.route) {
        MovieDetailsScreen(
            onBack = { navController.popBackStack() }
        )
    }
}

private fun NavController.navigateToSearch() {
    navigate(Destination.Search.route) {
        popUpTo(Destination.Splash.route) { inclusive = true }
        launchSingleTop = true
        restoreState = true
    }
}