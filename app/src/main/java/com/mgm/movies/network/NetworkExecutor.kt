package com.mgm.movies.network

import retrofit2.Call
import java.io.IOException

class NetworkExecutor<Api>(
    val networkService: Api,
    private val errorHandler: ErrorHandler
) {
    fun <T> execute(func: (Api) -> Call<T>): Resource<T> {
        return try {
            val response = func(networkService).execute()
            if (response.isSuccessful)
                Resource.Success<T>(response.body())
            else
                errorHandler.parseResponse<T>(response)

        } catch (e: IOException) {
            errorHandler.parseResponse(null)
        }
    }
}
