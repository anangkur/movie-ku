package com.anangkur.movieku.data

import android.content.Context
import com.anangkur.movieku.BuildConfig
import com.anangkur.movieku.data.local.LocalDataSource
import com.anangkur.movieku.data.local.SharedPreferenceHelper
import com.anangkur.movieku.data.remote.ApiService
import com.anangkur.movieku.data.remote.RemoteDataSource
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Injection {

    fun provideRepository(
        context: Context
    ) = Repository(
        provideLocalDataSource(context),
        provideRemoteDataSource(context)
    )

    fun provideApiService(
        context: Context
    ): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(ChuckerInterceptor(context))
                .build()
        )
        .build()
        .create(ApiService::class.java)

    private fun provideLocalDataSource(
        context: Context
    ) = LocalDataSource(
        provideSharedPreferencesHelper(context),
        context
    )

    private fun provideRemoteDataSource(
        context: Context
    ) = RemoteDataSource(provideApiService(context))

    private fun provideSharedPreferencesHelper(context: Context) = SharedPreferenceHelper(context)
}