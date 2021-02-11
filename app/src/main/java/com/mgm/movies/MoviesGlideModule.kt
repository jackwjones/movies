package com.mgm.movie

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.InputStream

@GlideModule
class MoviesGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain: Interceptor.Chain? ->
            val response = chain?.proceed(chain.request())

            response?.headers?.forEach {
                Timber.d("header (${it.first})${it.second}")
            }

            response!!
        }

        registry.replace(
            GlideUrl::class.java, InputStream::class.java,
            OkHttpUrlLoader.Factory(httpClient.build())
        )
    }
}