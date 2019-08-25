package com.anangkur.madesubmission1.data.remote

import com.anangkur.madesubmission1.BuildConfig.baseUrl
import com.anangkur.madesubmission1.data.model.Response
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService{

    @GET("{urlType}/{urlFilter}")
    suspend fun getData(@Path("urlType") urlType: String,
                @Path("urlFilter") urlFilter: String,
                @Query("api_key") apiKey: String,
                @Query("page") page: Int): Response

    @GET("search/{urlType}")
    suspend fun getSearchData(@Path("urlType") urlType: String,
                      @Query("api_key") apiKey: String,
                      @Query("query") query: String): Response

    @GET("discover/movie")
    suspend fun getTodayReleaseMovie(@Query("api_key") apiKey: String,
                             @Query("primary_release_date.gte") gteDate: String,
                             @Query("primary_release_date.lte") lteDate: String): Response

    companion object Factory{
        val getApiService: ApiService by lazy {

            val mClient =
                OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()

            val mRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build()

            mRetrofit.create(ApiService::class.java)
        }
    }
}