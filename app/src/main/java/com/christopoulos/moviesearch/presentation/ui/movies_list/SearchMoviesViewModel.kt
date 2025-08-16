package com.christopoulos.moviesearch.presentation.ui.movies_list

import com.christopoulos.moviesearch.domain.model.Movie
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christopoulos.moviesearch.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class MoviesListUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<Movie> = emptyList(),
    val page: Int = 1,
    val endReached: Boolean = false
)

@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    var state = androidx.compose.runtime.mutableStateOf(MoviesListUiState())
        private set

    private var searchJob: Job? = null

    // ΝΕΟ: επιλεγμένο genre (TMDB id)
    private var selectedGenreId: Int? = null
        set(value) {
            field = value
            // Κάθε αλλαγή genre κάνει reset τη λίστα
            state.value = state.value.copy(
                items = emptyList(),
                page = 1,
                endReached = false,
                error = null
            )
            triggerSearchOrDiscover()
        }

    fun getSelectedGenreId(): Int? = selectedGenreId

    fun onGenreToggle(genre: com.christopoulos.moviesearch.domain.model.MovieGenre) {
        selectedGenreId =
            if (selectedGenreId == genre.toTmdbId()) null else genre.toTmdbId()
    }

    // Χρησιμοποιούμε το mapping από MovieRepositoryImpl (ίδιο map για συνέπεια)
    private fun com.christopoulos.moviesearch.domain.model.MovieGenre.toTmdbId(): Int {
        return when (this.apiName) {
            "action" -> 28
            "adventure" -> 12
            "animation" -> 16
            "comedy" -> 35
            "crime" -> 80
            "drama" -> 18
            "fantasy" -> 14
            "mystery" -> 9648
            "romance" -> 10749
            "science_fiction" -> 878
            else -> error("Unknown TMDB genre: ${this.apiName}")
        }
    }

    fun onQueryChange(newQuery: String) {
        state.value = state.value.copy(query = newQuery)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400)
            triggerSearchOrDiscover()
        }
    }

    private fun triggerSearchOrDiscover() {
        val q = state.value.query.trim()
        when {
            q.isNotEmpty() -> search(reset = true)
            selectedGenreId != null -> discoverByGenre(reset = true)
            else -> {
                // empty state
                state.value = state.value.copy(
                    items = emptyList(),
                    isLoading = false,
                    error = null,
                    page = 1,
                    endReached = false
                )
            }
        }
    }

    fun retry() {
        val s = state.value
        if (s.items.isEmpty()) {
            triggerSearchOrDiscover()
        } else {
            loadNextPage()
        }
    }

    fun loadNextPage() {
        val s = state.value
        if (s.isLoading || s.endReached) return
        val q = s.query.trim()
        if (q.isNotEmpty()) search(reset = false)
        else if (selectedGenreId != null) discoverByGenre(reset = false)
    }

    private fun search(reset: Boolean) {
        val s = state.value
        val nextPage = if (reset) 1 else s.page + 1
        val q = s.query.trim()
        if (q.isBlank()) return

        viewModelScope.launch {
            state.value = s.copy(isLoading = true, error = null)
            try {
                val results = repository.searchMovies(q, nextPage)
                val newList = if (reset) results else s.items + results
                val endReached = results.isEmpty()
                state.value = s.copy(
                    items = newList,
                    isLoading = false,
                    page = nextPage,
                    endReached = endReached,
                    error = null
                )
            } catch (e: Exception) {
                state.value = s.copy(
                    isLoading = false,
                    error = e.message ?: "Κάτι πήγε στραβά"
                )
            }
        }
    }

    private fun discoverByGenre(reset: Boolean) {
        val s = state.value
        val nextPage = if (reset) 1 else s.page + 1
        val gid = selectedGenreId ?: return

        viewModelScope.launch {
            state.value = s.copy(isLoading = true, error = null)
            try {
                val results = repository.getMoviesByGenre(gid, nextPage)
                val newList = if (reset) results else s.items + results
                val endReached = results.isEmpty()
                state.value = s.copy(
                    items = newList,
                    isLoading = false,
                    page = nextPage,
                    endReached = endReached,
                    error = null
                )
            } catch (e: Exception) {
                state.value = s.copy(
                    isLoading = false,
                    error = e.message ?: "Κάτι πήγε στραβά"
                )
            }
        }
    }
}