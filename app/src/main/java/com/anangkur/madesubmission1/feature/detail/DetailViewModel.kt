package com.anangkur.madesubmission1.feature.detail

import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.model.Result

class DetailViewModel(private val repository: Repository){

    val resultLive = MutableLiveData<Result>()
    val languageLive = MutableLiveData<String>()

    fun getDataFromIntent(data: Result){
        resultLive.value = data
    }

    fun loadLanguageSetting(){
        languageLive.value = repository.loadLanguage()
    }
}