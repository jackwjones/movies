package com.mgm.movie.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun getConext(application: Application): Context = application

    @Provides
    fun getGson(): Gson {
        return GsonBuilder()
            .create()
    }
}
