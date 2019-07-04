package com.anangkur.madesubmission1.feature.main.movie

import com.anangkur.madesubmission1.data.model.Response
import com.google.gson.Gson

class MoviePresenter (private val view: MovieView){
    fun createHorizontalData(json: String){
        val response = Gson().fromJson(json, Response::class.java)
        val data = response.results
        view.onHorizontalDataReady(data)
    }

    fun createVerticalData(json: String){
        val response = Gson().fromJson(json, Response::class.java)
        val data = response.results
        view.onVerticalDataReady(data)
    }
}