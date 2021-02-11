package com.mgm.movies.network

import androidx.annotation.StringRes

sealed class Resource<T>(val content: T?) {
    class Success<T>(content: T?) : Resource<T>(content)
    class Loading<T>(content: T? = null) : Resource<T>(content)
    class Error<T>(
        content: T?,
        val code: Int = UNKNOWN_ERROR,
        val error: String? = null,
        @StringRes val title: Int? = null
    ) : Resource<T>(content)

    fun isSuccess(): Boolean = this is Success<*>

    fun <R> mapContent(transform: (T?) -> R?): Resource<R> {
        val newContent = transform(content)
        return when (this) {
            is Success -> Success(newContent)
            is Loading -> Loading(newContent)
            is Error -> Error(newContent, code, error, title)
        }
    }

    companion object {
        const val UNKNOWN_ERROR = -1
        operator fun <T> invoke(content: T): Resource<T> = Success(content)
    }
}
