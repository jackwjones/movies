package com.mgm.movies.network.api

import com.mgm.movies.network.NetworkConstants
import com.mgm.movies.network.model.DiscoverResponse
import com.mgm.movies.network.model.MovieDetailResponse
import com.mgm.movies.network.model.ReviewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface TmdbApiService {

    @GET("discover/movie?sort_by=popularity.desc")
    fun getMoviesPopular(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) authToken: String
    ): Call<DiscoverResponse>

    @GET("movie/{id}")
    fun getMovieDetails(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) authToken: String,
        @Path("id") id: Int
    ): Call<MovieDetailResponse>

    @GET("movie/{id}/reviews")
    fun getMovieReviews(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) authToken: String,
        @Path("id") id: Int
    ): Call<ReviewsResponse>
}
