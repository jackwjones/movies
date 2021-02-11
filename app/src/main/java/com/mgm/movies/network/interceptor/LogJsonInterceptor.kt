package com.mgm.movies.network.interceptor

import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import timber.log.Timber
import java.io.IOException

class LogJsonInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val rawJson = response.body?.string() ?: JsonObject().asString
        Timber.d(rawJson)

        // Re-create the response before returning it because body can be read only once
        val body = rawJson?.toResponseBody(response.body?.contentType())
        return response.newBuilder().body(body).build()
    }
}
