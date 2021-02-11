package com.mgm.movies.network.model

import com.google.gson.annotations.SerializedName

data class DiscoverResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<MovieListResult>,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int
)