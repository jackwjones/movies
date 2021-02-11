package com.mgm.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mgm.movie.di.ViewModelFactory
import com.mgm.movies.R
import com.mgm.movies.databinding.FragmentMovieListBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MovieListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(MovieListViewModel::class.java)
    }

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var moviesAdapter: MovieListAdapter

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
            R.layout.fragment_movie_list, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this
        binding.viewModel = viewModel

        setupViews()
        observe()

        viewModel.fetchPopularMovies(resources.getString(R.string.movie_list_popular))

        return binding.root
    }

    fun setupViews() {
    }

    fun observe() {
        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { movies ->
            moviesAdapter = MovieListAdapter(requireContext(), movies)
            moviesAdapter.selectionCallBack = { movieSelected(it) }
            binding.moviesRecycler.swapAdapter(moviesAdapter, true)
        })
    }

    fun movieSelected(id: Int) {
        val action = MovieListFragmentDirections.actionNavMovieListToNavMovieDetails(id)
        findNavController().navigate(action)
    }
}