package com.mgm.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgm.movies.data.entities.Review
import com.mgm.movies.data.repository.MovieRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MovieReviewsViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    fun fetchReviews(id: Int, category: String) {
        viewModelScope.launch {
            val response = movieRepository.getMovieReviews(id)
            if (response.isSuccess()) {
                val reviews = ArrayList<Review>()
                for (review in response.content?.results ?: emptyList()) {
                    reviews.add(
                        Review(
                            id = review.id,
                            author = review.author,
                            content = review.content,
                            category = category
                        )
                    )
                }
                _reviews.value = reviews
            } else {
                Timber.d("error getting movie reviews")
            }
        }
    }
}
