package com.mgm.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgm.movies.data.entities.MovieDetails
import com.mgm.movies.data.repository.MovieRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _details = MutableLiveData<MovieDetails>()
    val details: LiveData<MovieDetails> = _details

    fun fetchDetails(id: Int) {
        viewModelScope.launch {
            val response = movieRepository.getMovieDetails(id)
            if (response.isSuccess()) {
                val details = response.content
                if (details != null)
                    _details.value = MovieDetails(
                        id = details.id,
                        title = details.title,
                        posterUrl = details.poster_path,
                        adult = details.adult,
                        backdrop_path = details.backdrop_path,
                        budget = details.budget,
                        overview = details.overview,
                        popularity = details.popularity,
                        release_date = details.release_date,
                        revenue = details.revenue,
                        runtime = details.runtime,
                        status = details.status,
                        tagline = details.tagline,
                        vote_average = details.vote_average,
                        vote_count = details.vote_count
                    )
            } else {
                Timber.d("error getting movie details")
            }
        }
    }
}
