package com.mgm.movies.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import timber.log.Timber
import java.io.IOException

class LogRequestBodyInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val copy = request.newBuilder().build()
        val buffer = Buffer()
        copy.body?.writeTo(buffer)
        Timber.d(buffer.readUtf8())

        return chain.proceed(request)
    }
}
