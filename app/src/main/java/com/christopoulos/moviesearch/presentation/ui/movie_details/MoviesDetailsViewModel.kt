package com.christopoulos.moviesearch.presentation.ui.movie_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christopoulos.moviesearch.data.repository.MovieRepository
import com.christopoulos.moviesearch.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

data class MovieDetailsUiState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String? = null
)

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state = androidx.compose.runtime.mutableStateOf(MovieDetailsUiState())
        private set

    private val movieId: Int? = savedStateHandle.get<Int>("movieId")

    init {
        movieId?.let { load(it) }
    }

    fun retry() {
        movieId?.let { load(it) }
    }

    fun load(id: Int) {
        viewModelScope.launch {
            state.value = MovieDetailsUiState(isLoading = true)
            try {
                val movie = repository.getMovieDetails(id)
                state.value = MovieDetailsUiState(isLoading = false, movie = movie)
            } catch (e: Exception) {
                state.value = MovieDetailsUiState(
                    isLoading = false,
                    error = e.message ?: "Αποτυχία φόρτωσης λεπτομερειών"
                )
            }
        }
    }
}