package com.christopoulos.moviesearch.data.remote

import com.christopoulos.moviesearch.domain.model.Movie

private const val IMAGE_BASE = "https://image.tmdb.org/t/p/w500"

fun MovieSummary.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        posterPath = this.poster_path,
        backdropPath = null,
        rating = this.vote_average,
        voteCount = this.vote_count ?: 0,
        description = this.overview.orEmpty(),
        releaseDate = this.release_date.orEmpty(),
        imageUrl = this.poster_path?.let { "$IMAGE_BASE$it" }
    )
}

fun MovieDetailsDto.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        posterPath = this.poster_path,
        backdropPath = this.backdrop_path,
        rating = this.vote_average,
        voteCount = this.vote_count ?: 0,
        description = this.overview.orEmpty(),
        releaseDate = this.release_date.orEmpty(),
        imageUrl = this.poster_path?.let { "$IMAGE_BASE$it" }
    )
}

data class SearchMoviesResponse(
    val page: Int,
    val results: List<MovieSummary>,
    val total_pages: Int,
    val total_results: Int
)

data class DiscoverMoviesResponse(
    val page: Int,
    val results: List<MovieSummary>,
    val total_pages: Int,
    val total_results: Int
)

data class GenresResponse(
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)

data class MovieSummary(
    val id: Int,
    val title: String,
    val overview: String?,
    val poster_path: String?,
    val vote_average: Double,
    val release_date: String?,
    val vote_count: Int?
)

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Double,
    val genres: List<Genre>,
    val release_date: String?,
    val vote_count: Int?
)

