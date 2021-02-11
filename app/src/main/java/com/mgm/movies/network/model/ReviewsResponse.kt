package com.mgm.movies.network.model

import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<ReviewListResult>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)