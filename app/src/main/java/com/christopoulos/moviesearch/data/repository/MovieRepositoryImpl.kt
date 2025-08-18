package com.christopoulos.moviesearch.data.repository

import com.christopoulos.moviesearch.data.remote.TmdbApi
import com.christopoulos.moviesearch.domain.model.Movie
import com.christopoulos.moviesearch.data.remote.toDomain
import com.christopoulos.moviesearch.domain.model.MovieGenre

// MovieRepositoryImpl: Υλοποιεί το abstraction του domain repository.
// - Αναθέτει καλέσματα στο Retrofit API
// - Μετατρέπει DTOs σε domain models
// - Παρέχει mapping genres -> TMDB ids για εύκολη χρήση από UI/domain
class MovieRepositoryImpl(
    private val api: TmdbApi
) : MovieRepository {

    override suspend fun searchMovies(query: String, page: Int): List<Movie> {
        val resp = api.searchMovies(
            query = query.trim(),
            page = page.coerceAtLeast(1),
            includeAdult = false,
            language = "en-US"
        )
        return resp.results.map { it.toDomain() }
    }

    override suspend fun getMoviesByGenre(genreId: Int, page: Int): List<Movie> {
        val resp = api.discoverMovies(
            withGenres = genreId.toString(),
            page = page.coerceAtLeast(1),
            includeAdult = false,
            language = "en-US"
        )
        return resp.results.map { it.toDomain() }
    }

    override suspend fun getMovieDetails(id: Int): Movie {
        val details = api.getMovieDetails(
            movieId = id,
            language = "en-US"
        )
        return details.toDomain()
    }

    // TMDB Genre IDs: https://developer.themoviedb.org/reference/genre-movie-list
    val GENRE_NAME_TO_ID = mapOf(
        "action" to 28,
        "adventure" to 12,
        "animation" to 16,
        "comedy" to 35,
        "crime" to 80,
        "drama" to 18,
        "fantasy" to 14,
        "mystery" to 9648,
        "romance" to 10749,
        "science_fiction" to 878
    )

    fun MovieGenre.toTmdbId(): Int = GENRE_NAME_TO_ID[this.apiName]
        ?: error("Unknown TMDB genre for ${this.apiName}")
}