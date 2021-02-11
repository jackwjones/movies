package com.mgm.movies.data.entities

data class Movie(
    val id: Long,
    val title: String,
    val voteAverage: Number,
    val category: String,
    val posterUrl: String?
)
