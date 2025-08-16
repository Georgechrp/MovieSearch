package com.christopoulos.moviesearch.data.repository

import com.christopoulos.moviesearch.domain.model.Movie

interface MovieRepository {
    suspend fun searchMovies(query: String, page: Int): List<Movie>
    suspend fun getMoviesByGenre(genreId: Int, page: Int): List<Movie>
    suspend fun getMovieDetails(id: Int): Movie
}