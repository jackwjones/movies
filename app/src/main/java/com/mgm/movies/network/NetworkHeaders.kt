package com.mgm.movies.network

import com.mgm.movies.BuildConfig

class NetworkHeaders {
    val version: String get() = BuildConfig.VERSION_NAME
    val platform: String get() = NetworkConstants.CLIENT_PLATFORM_ANDROID
    val appName: String get() = "Movie Recs"
    val contentType: String get() = NetworkConstants.CLIENT_CONTENT_TYPE_JSON_UTF8
}
