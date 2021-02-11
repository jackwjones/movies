package com.mgm.movies.data.repository

import com.mgm.movies.network.NetworkConstants
import com.mgm.movies.network.NetworkExecutor
import com.mgm.movies.network.Resource
import com.mgm.movies.network.api.TmdbApiService
import com.mgm.movies.network.model.DiscoverResponse
import com.mgm.movies.network.model.MovieDetailResponse
import com.mgm.movies.network.model.ReviewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val tmdbApiService: NetworkExecutor<TmdbApiService>
) {
    val auth = "${NetworkConstants.BEARER} ${NetworkConstants.AUTH_TOKEN}"

    suspend fun getMoviesPopular(): Resource<DiscoverResponse> {
        return withContext(Dispatchers.IO) {
            tmdbApiService.execute { api ->
                api.getMoviesPopular(auth)
            }
        }
    }

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetailResponse> {
        return withContext(Dispatchers.IO) {
            tmdbApiService.execute { api ->
                api.getMovieDetails(auth, movieId)
            }
        }
    }

    suspend fun getMovieReviews(movieId: Int): Resource<ReviewsResponse> {
        return withContext(Dispatchers.IO) {
            tmdbApiService.execute { api ->
                api.getMovieReviews(auth, movieId)
            }
        }
    }
}