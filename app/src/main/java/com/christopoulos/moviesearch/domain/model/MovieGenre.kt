package com.christopoulos.moviesearch.domain.model

import com.christopoulos.moviesearch.R

enum class MovieGenre(val displayName: String, val apiName: String) {
    ACTION("Action", "action"),
    ADVENTURE("Adventure", "adventure"),
    ANIMATION("Animation", "animation"),
    COMEDY("Comedy", "comedy"),
    DRAMA("Drama", "drama"),
    FANTASY("Fantasy", "fantasy"),
    CRIME("Crime", "crime"),
    MYSTERY("Mystery", "mystery"),
    ROMANCE("Romance", "romance"),
    SCIENCE_FICTION("Science Fiction", "science_fiction");

    companion object {
        val default10 = listOf(ACTION, ADVENTURE, ANIMATION, COMEDY, DRAMA, FANTASY, CRIME, MYSTERY, ROMANCE, SCIENCE_FICTION)
    }
}


fun typeIconRes(type: MovieGenre): Int = when (type) {
    MovieGenre.ACTION   -> R.drawable.action
    MovieGenre.ADVENTURE     -> R.drawable.adventure
    MovieGenre.ANIMATION    -> R.drawable.animation
    MovieGenre.COMEDY -> R.drawable.comedy
    MovieGenre.DRAMA    -> R.drawable.drama
    MovieGenre.FANTASY      -> R.drawable.fantasy
    MovieGenre.CRIME -> R.drawable.crime
    MovieGenre.MYSTERY   -> R.drawable.mystery
    MovieGenre.ROMANCE   -> R.drawable.romance
    MovieGenre.SCIENCE_FICTION   -> R.drawable.scifi
}