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

