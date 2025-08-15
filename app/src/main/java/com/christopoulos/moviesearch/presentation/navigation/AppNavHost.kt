package com.christopoulos.moviesearch.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.christopoulos.moviesearch.presentation.ui.movies_genres.MoviesGenresScreen
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
        moviesGenres(navController)
        moviesList(navController)
        detailsGraph(navController)
    }
}

private fun NavGraphBuilder.splashGraph(navController: NavController) {
    composable(Destination.Splash.route) {
        SplashScreen(onTimeout = { navController.navigateToTypeSelection() })
    }
}

private fun NavGraphBuilder.moviesGenres(navController: NavController) {
    composable(Destination.MovieGenres.route) {
        MoviesGenresScreen (
            onTypeSelected = { type ->
                navController.navigate(Destination.typeListRoute(type.apiName))
            }
        )
    }
}

private fun NavGraphBuilder.moviesList(navController: NavController) {


}


private fun NavGraphBuilder.detailsGraph(navController: NavController) {


}

private fun NavController.navigateToTypeSelection() {
    navigate(Destination.MovieGenres.route) {
        popUpTo(Destination.Splash.route) { inclusive = true }
        launchSingleTop = true
        restoreState = true
    }
}