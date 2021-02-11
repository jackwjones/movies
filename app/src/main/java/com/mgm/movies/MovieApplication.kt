package com.mgm.movies

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.mgm.movie.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class MovieApplication : Application(), HasSupportFragmentInjector, HasActivityInjector {

    @Inject
    lateinit var fragmentSupportInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentSupportInjector
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    // Timber Support
    class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            // implement for Crashlytics, etc.
        }

    }
}