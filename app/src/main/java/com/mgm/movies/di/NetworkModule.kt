package com.mgm.movies.di

import android.content.Context
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mgm.movies.network.*
import com.mgm.movies.network.api.TmdbApiService
import com.mgm.movies.network.interceptor.ErrorHandlingInterceptor
import com.mgm.movies.network.interceptor.HeadersInterceptor
import com.mgm.movies.network.interceptor.LogJsonInterceptor
import com.mgm.movies.network.interceptor.LogRequestBodyInterceptor
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    fun getOkHttpBuilder(
        @Named(INTERCEPTOR_LOGGER) loggerInterceptor: Interceptor,
        @Named(INTERCEPTOR_HEADERS) headerInterceptor: Interceptor,
        @Named(INTERCEPTOR_JSON_LOGGER) jsonInterceptor: Interceptor,
        @Named(INTERCEPTOR_ERROR_HANDLER) errorHandlingInterceptor: Interceptor,
        @Named(INTERCEPTOR_REQUEST_LOGGER) requestBodyInterceptor: Interceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(errorHandlingInterceptor)
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggerInterceptor)
            .addInterceptor(requestBodyInterceptor)
            .addInterceptor(jsonInterceptor)
    }

    @Provides
    @Singleton
    @Named(OKHTTPCLIENT_MAIN)
    fun getOkHttpMain(builder: OkHttpClient.Builder): OkHttpClient {
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideErrorHandler(context: Context, gson: Gson): ErrorHandler =
        DefaultErrorHandler(context, gson)

    @Provides
    @Singleton
    @Named(INTERCEPTOR_LOGGER)
    fun provideLoggerInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return interceptor
    }

    @Provides
    @Singleton
    @Named(INTERCEPTOR_REQUEST_LOGGER)
    fun provideRequestLoggerInterceptor(): Interceptor = LogRequestBodyInterceptor()

    @Provides
    @Singleton
    @Named(INTERCEPTOR_ERROR_HANDLER)
    fun provideErrorHandlerInterceptor(): Interceptor = ErrorHandlingInterceptor()

    @Provides
    @Singleton
    @Named(INTERCEPTOR_JSON_LOGGER)
    fun provideLogJsonInterceptor(): Interceptor = LogJsonInterceptor()

    @Provides
    @Singleton
    @Named(INTERCEPTOR_HEADERS)
    fun provideHeaderInterceptor(): Interceptor = HeadersInterceptor(NetworkHeaders())

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
    }

    @Provides
    @Singleton
    @Named(RETROFIT_MAIN)
    fun provideRetrofitMain(
        builder: Retrofit.Builder,
        @Named(OKHTTPCLIENT_MAIN) okHttpClient: OkHttpClient
    ): Retrofit {
        return builder
            .baseUrl(NetworkConstants.API_URL_BASE)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideTmdbApi(@Named(RETROFIT_MAIN) retrofit: Retrofit): TmdbApiService =
        retrofit.create(TmdbApiService::class.java)

    @Provides
    @Singleton
    fun provideDnaDemoApiExecutor(
        api: TmdbApiService,
        errorHandler: ErrorHandler
    ): NetworkExecutor<TmdbApiService> = NetworkExecutor(api, errorHandler)

    companion object {
        const val OKHTTPCLIENT_MAIN: String = "OKHTTPCLIENT_MAIN"
        const val RETROFIT_MAIN: String = "RETROFIT_MAIN"
        const val RETROFIT_CONFIG: String = "RETROFIT_CONFIG"
        const val INTERCEPTOR_HEADERS: String = "INTERCEPTOR_HEADERS"
        const val INTERCEPTOR_JSON_LOGGER: String = "INTERCEPTOR_JSON_LOGGER"
        const val INTERCEPTOR_LOGGER: String = "INTERCEPTOR_LOGGER"
        const val INTERCEPTOR_REQUEST_LOGGER: String = "INTERCEPTOR_REQUEST_LOGGER"
        const val INTERCEPTOR_ERROR_HANDLER: String = "INTERCEPTOR_ERROR_HANDLER"
    }
}