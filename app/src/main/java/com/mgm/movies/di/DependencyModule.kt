package com.mgm.movie.di

import androidx.lifecycle.ViewModel
import com.mgm.movies.data.repository.MovieRepository
import com.mgm.movies.ui.MovieDetailsViewModel
import com.mgm.movies.ui.MovieListViewModel
import com.mgm.movies.ui.MovieReviewsViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class DependencyModule {
    @Provides
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    fun getMovieListViewModel(
        movieRepository: MovieRepository
    ): ViewModel =
        MovieListViewModel(movieRepository)

    @Provides
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    fun getMovieDetailsViewModel(
        movieRepository: MovieRepository
    ): ViewModel =
        MovieDetailsViewModel(movieRepository)

    @Provides
    @IntoMap
    @ViewModelKey(MovieReviewsViewModel::class)
    fun getMovieReviewsViewModel(
        movieRepository: MovieRepository
    ): ViewModel =
        MovieReviewsViewModel(movieRepository)
}
