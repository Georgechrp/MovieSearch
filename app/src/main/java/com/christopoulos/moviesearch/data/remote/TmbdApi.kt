package com.christopoulos.moviesearch.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    // Search movies by keyword
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US"
    ): SearchMoviesResponse

    // Genres list
    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String = "en-US"
    ): GenresResponse

    // Discover movies by genre(s)
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") withGenres: String, // π.χ. "878" ή "28,12" (AND) ή "28|12" (OR)
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US"
    ): DiscoverMoviesResponse

    // Movie details by id
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieDetailsDto
}