package com.mgm.movie.di

import com.mgm.movies.ui.MovieDetailsFragment
import com.mgm.movies.ui.MovieListFragment
import com.mgm.movies.ui.MovieReviewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {
    @ContributesAndroidInjector
    abstract fun movieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun movieDetailsFragment(): MovieDetailsFragment

    @ContributesAndroidInjector
    abstract fun movieReviewsFragment(): MovieReviewsFragment
}
