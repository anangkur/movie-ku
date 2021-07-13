package com.anangkur.movieku.data.remote

import com.anangkur.movieku.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{

    @GET("{urlType}/{urlFilter}")
    suspend fun getData(
        @Path("urlType") urlType: String,
        @Path("urlFilter") urlFilter: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response

    @GET("search/{urlType}")
    suspend fun getSearchData(
        @Path("urlType") urlType: String,
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Response

    @GET("discover/movie")
    suspend fun getTodayReleaseMovie(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") gteDate: String,
        @Query("primary_release_date.lte") lteDate: String
    ): Response
}