package com.mgm.movies.network

data class NetworkErrorDetails (
    val code: String,
    val message: String,
    val data: Any
)
