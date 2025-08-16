package com.christopoulos.moviesearch.di

import com.christopoulos.moviesearch.data.remote.TmdbApi
import com.christopoulos.moviesearch.data.repository.MovieRepository
import com.christopoulos.moviesearch.data.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(api: TmdbApi): MovieRepository = MovieRepositoryImpl(api)
}