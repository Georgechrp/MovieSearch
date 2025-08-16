package com.christopoulos.moviesearch.presentation.ui.movies_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.hilt.navigation.compose.hiltViewModel
import coil.request.ImageRequest
import com.christopoulos.moviesearch.R
import com.christopoulos.moviesearch.domain.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: SearchMoviesViewModel = hiltViewModel()
) {
    val ui by viewModel.state

    Scaffold(
        topBar = { SmallTopAppBar(title = { Text("Movie Search") }) }
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp)
        ) {
            var text by remember { mutableStateOf(TextFieldValue(ui.query)) }
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    viewModel.onQueryChange(it.text)
                },
                placeholder = { Text("Search keyword (e.g. alien)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // ΝΕΟ: Οριζόντια μπάρα φίλτρων (genre chips)
            GenreChipsBar(
                selectedGenreId = viewModel.getSelectedGenreId(),
                onToggle = { viewModel.onGenreToggle(it) }
            )

            Spacer(Modifier.height(8.dp))

            if (ui.error != null && ui.items.isEmpty()) {
                Text("Σφάλμα: ${ui.error}", color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
                Button(onClick = { viewModel.retry() }) { Text("Δοκίμασε ξανά") }
            } else if (ui.items.isEmpty() && ui.query.isBlank() && viewModel.getSelectedGenreId() == null) {
                // Empty state οδηγία
                Text(
                    "Γράψε ένα keyword για αναζήτηση ή επίλεξε κατηγορία από πάνω.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                MoviesList(
                    items = ui.items,
                    isLoading = ui.isLoading,
                    onEndReached = { viewModel.loadNextPage() },
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
private fun GenreChipsBar(
    selectedGenreId: Int?,
    onToggle: (com.christopoulos.moviesearch.domain.model.MovieGenre) -> Unit
) {
    val genres = remember { com.christopoulos.moviesearch.domain.model.MovieGenre.default10 }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(genres.size) { i ->
            val g = genres[i]
            val gid = when (g.apiName) {
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
                else -> -1
            }
            FilterChip(
                selected = selectedGenreId == gid,
                onClick = { onToggle(g) },
                label = { Text(g.displayName) }
            )
        }
    }
}

@Composable
private fun MoviesList(
    items: List<Movie>,
    isLoading: Boolean,
    onEndReached: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        itemsIndexed(items) { index, movie ->
            if (index == items.lastIndex - 3) {
                onEndReached()
            }
            MovieRow(movie = movie, onClick = { onMovieClick(movie.id) })
            Divider()
        }

        if (isLoading) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun MovieRow(
    movie: Movie,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(onClick = onClick) // clickable όλο το row
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = movie.title,
            placeholder = painterResource(R.drawable.placeholder_wide),
            error = painterResource(R.drawable.placeholder_error),
            modifier = Modifier.size(100.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(movie.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text(movie.description, maxLines = 3, style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(6.dp))
            Text(
                "Rating: ${"%.1f".format(movie.rating)} (${movie.voteCount})",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}