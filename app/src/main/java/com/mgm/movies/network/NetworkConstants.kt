package com.mgm.movies.network

object NetworkConstants {
    const val AUTH_TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZjQwNmZhNmExMGY3OTBhZjZlZDBiNGQ3NTA4MDY1OCIsInN1YiI6IjYwMDQ3NGY0ZThkMGI0MDAzZjJjYjBlMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ENhnFia2v1oX1Bzk21ijuwmeaVI06xaiRGrlQbbMtrc"

    const val API_URL_BASE: String = "https://api.themoviedb.org/3/"

    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_CONTENT_TYPE = "Content-Type"
    const val HEADER_CLIENT_VERSION = "CLIENT_VERSION"
    const val HEADER_CLIENT_PLATFORM = "CLIENT_PLATFORM"
    const val HEADER_CLIENT_APP = "CLIENT_APP"

    const val CLIENT_PLATFORM_ANDROID = "android"
    const val CLIENT_CONTENT_TYPE_JSON_UTF8 = "application/json;charset=utf-8"

    const val BEARER = "Bearer"
    const val ERROR_DETAILS = "ErrorDetails"
}
