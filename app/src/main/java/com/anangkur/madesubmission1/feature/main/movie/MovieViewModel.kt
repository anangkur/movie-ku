package com.anangkur.madesubmission1.feature.main.movie

import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.model.Response
import com.anangkur.madesubmission1.data.model.Result
import com.google.gson.Gson

class MovieViewModel (private val repository: Repository){

    val horizontalDataLive = MutableLiveData<List<Result>>()
    val verticalLiveData = MutableLiveData<List<Result>>()

    fun createHorizontalData(json: String){
        val response = Gson().fromJson(json, Response::class.java)
        val data = response.results
        horizontalDataLive.value = data
    }

    fun createVerticalData(json: String){
        val response = Gson().fromJson(json, Response::class.java)
        val data = response.results
        verticalLiveData.value = data
    }
}