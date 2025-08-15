package com.christopoulos.moviesearch.presentation.ui.movies_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.christopoulos.moviesearch.domain.model.MovieGenre
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesGenresViewModel @Inject constructor() : ViewModel() {
    val types: List<MovieGenre> = MovieGenre.default10

    var selected: MovieGenre? by mutableStateOf(null)
        private set

    fun onTypeClick(type: MovieGenre) {
        selected = if (selected == type) null else type
    }
}