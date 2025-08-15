package com.christopoulos.moviesearch.presentation.navigation

object Destination {
    object Splash { const val route = "splash" }
    object MovieGenres { const val route = "movies_genres" }
    fun typeListRoute(typeName: String) = "type/$typeName"

    object Details { const val route = "details/{name}" }
    fun detailsRoute(name: String) = "details/$name"
}