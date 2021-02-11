package com.mgm.movies.network

import android.content.Context
import androidx.annotation.CallSuper
import com.google.gson.Gson
import retrofit2.Response
import timber.log.Timber

interface ErrorHandler {
    fun <T> parseResponse(response: Response<T>?): Resource<T>
}

open class DefaultErrorHandler(
    protected val context: Context,
    protected val gson: Gson
) : ErrorHandler {

    @Suppress("RemoveExplicitTypeArguments")
    override fun <T> parseResponse(response: Response<T>?): Resource<T> {
        return response?.let {
            val errorResponse = response.headers()[NetworkConstants.ERROR_DETAILS] ?: "UNKNOWN ERROR"
            val errorDetails = NetworkErrorDetails("-1", errorResponse, 0)
            errorDetails?.let { err ->
                onError<T>(response.code(), err)
            } ?: it.errorBody()?.let { err ->
                logError(err.string())
                Resource.Error<T>(null, response.code(), errorDetails?.message)
            }
        } ?: return Resource.Error<T>(null, Resource.UNKNOWN_ERROR)
    }

    @CallSuper
    protected open fun <T> onError(code: Int, errorDetails: NetworkErrorDetails): Resource<T> {
        logError(errorDetails.message)

        return Resource.Error(null, code, errorDetails.message)
    }

    private fun logError(error: String) {
        Timber.e(error)
    }
}
