package com.anangkur.madesubmission1.feature.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.model.Result

class DetailViewModel(application: Application, private val repository: Repository): AndroidViewModel(application){

    val resultLive = MutableLiveData<Result>()

    fun getDataFromIntent(data: Result){
        resultLive.value = data
    }
}