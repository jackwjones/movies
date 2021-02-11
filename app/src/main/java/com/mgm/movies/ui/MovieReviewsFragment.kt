package com.mgm.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.mgm.movie.di.ViewModelFactory
import com.mgm.movies.R
import com.mgm.movies.databinding.FragmentMovieReviewsBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MovieReviewsFragment : Fragment() {

    val args: MovieReviewsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(MovieReviewsViewModel::class.java)
    }

    private lateinit var binding: FragmentMovieReviewsBinding
    private lateinit var reviewsAdapter: MovieReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_reviews, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this
        binding.viewModel = viewModel

        setupViews()
        observe()

        return binding.root
    }

    fun setupViews() {
        viewModel.fetchReviews(args.movieId, resources.getString(R.string.latest))
    }

    fun observe() {
        viewModel.reviews.observe(viewLifecycleOwner, Observer { reviews ->
            reviewsAdapter = MovieReviewsAdapter(requireContext(), reviews)
            binding.reviewsRecycler.swapAdapter(reviewsAdapter, true)
        })
    }
}