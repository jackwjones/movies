package com.mgm.movie.util

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.mgm.movies.network.NetworkConstants

fun String.toGlideUrl(): GlideUrl {
    return GlideUrl(
        this,
        LazyHeaders.Builder()
            .addHeader(
                NetworkConstants.HEADER_AUTHORIZATION,
                "${NetworkConstants.BEARER} ${NetworkConstants.AUTH_TOKEN}"
            )
            .addHeader("Accept", "image/png")
            .build()
    )
}