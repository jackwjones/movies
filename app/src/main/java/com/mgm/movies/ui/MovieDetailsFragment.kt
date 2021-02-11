package com.mgm.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mgm.movie.di.ViewModelFactory
import com.mgm.movies.R
import com.mgm.movies.databinding.FragmentMovieDetailsBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(MovieDetailsViewModel::class.java)
    }

    private lateinit var binding: FragmentMovieDetailsBinding

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
            R.layout.fragment_movie_details, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this
        binding.viewModel = viewModel

        setupViews()
        observe()

        return binding.root
    }

    fun setupViews() {
        viewModel.fetchDetails(args.movieId)
    }

    fun observe() {
    }

    fun showReviews(view: View) {
        val action =
            MovieDetailsFragmentDirections.actionNavMovieDetailsToMovieReviewsFragment(args.movieId)
        findNavController().navigate(action)
    }
}