package com.mgm.movies.network.interceptor

import com.mgm.movies.network.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class ErrorHandlingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)
        val bodyJson = response.body?.string()
        if (!response.isSuccessful && bodyJson!!.startsWith("{", true)) {
            return response.newBuilder()
                .addHeader(NetworkConstants.ERROR_DETAILS, bodyJson.replace("\n", ""))
                .body(bodyJson.toResponseBody(response.body?.contentType())).build()
        }

        // Re-create the response before returning it because body can be read only once
        val body = bodyJson?.toResponseBody(response.body?.contentType())
        return response.newBuilder().body(body).build()
    }
}
