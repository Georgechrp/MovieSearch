package com.christopoulos.moviesearch.presentation.ui.movie_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    onBack: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val ui by viewModel.state

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(ui.movie?.title ?: "Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { inner ->
        when {
            ui.isLoading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(inner),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            ui.error != null -> {
                Column(Modifier.padding(inner).padding(16.dp)) {
                    Text("Σφάλμα: ${ui.error}", color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.retry() }) {
                        Text("Δοκίμασε ξανά")
                    }
                }
            }
            else -> {
                ui.movie?.let { movie ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(inner)
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = movie.imageUrl,
                            contentDescription = movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(movie.title, style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(8.dp))
                        Text("Rating: ${"%.1f".format(movie.rating)} (${movie.voteCount})")
                        Spacer(Modifier.height(12.dp))
                        Text(movie.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}