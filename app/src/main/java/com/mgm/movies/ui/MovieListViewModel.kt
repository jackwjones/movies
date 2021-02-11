package com.mgm.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgm.movies.data.entities.Movie
import com.mgm.movies.data.repository.MovieRepository
import com.mgm.movies.network.NetworkConstants
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    fun fetchPopularMovies(categoryName: String) {
        viewModelScope.launch {
            val response = movieRepository.getMoviesPopular()
            if (response.isSuccess()) {
                val movies = ArrayList<Movie>()
                for (movie in response.content?.results ?: emptyList()) {
                    movies.add(
                        Movie(
                            id = movie.id.toLong(),
                            title = movie.title,
                            voteAverage = movie.voteAverage,
                            category = categoryName,
                            posterUrl = "${NetworkConstants.API_URL_BASE}${movie.posterPath}"
                        )
                    )
                }
                _popularMovies.value = movies
            } else {
                Timber.d("error getting movie list")
            }
        }
    }
}
