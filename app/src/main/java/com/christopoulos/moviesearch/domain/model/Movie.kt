package com.christopoulos.moviesearch.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val rating: Double,
    val voteCount: Int,
    val description: String,
    val releaseDate: String,
    val imageUrl: String?
)