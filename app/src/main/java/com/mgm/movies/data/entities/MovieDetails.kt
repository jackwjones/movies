package com.mgm.movies.data.entities

data class MovieDetails(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val adult: Boolean,
    val backdrop_path: String?,
    val budget: Int,
    val overview: String?,
    val popularity: Number,
    val release_date: String,
    val revenue: Int,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    val vote_average: Number,
    val vote_count: Int
) {
    val stringBudget get() = budget.toString()
    val stringPopularity get() = popularity.toString()
    val stringRevenue get() = revenue.toString()
    val stringRuntime get() = runtime?.toString() ?: ""
    val stringVoteAverage get() = vote_average.toString()
    val stringVoteCount get() = vote_count.toString()
}
