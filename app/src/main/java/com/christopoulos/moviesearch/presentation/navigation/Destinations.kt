package com.christopoulos.moviesearch.presentation.navigation

object Destination {
    object Splash { const val route = "splash" }
    object MovieGenres { const val route = "movies_genres" }

    // Αναζήτηση (κύρια λίστα αναζήτησης)
    object Search { const val route = "search" }

    // Λεπτομέρειες με movieId
    object Details { const val route = "details/{movieId}" }
    fun detailsRoute(movieId: Int) = "details/$movieId"

    // Προϋπάρχει
    fun typeListRoute(typeName: String) = "type/$typeName"
}