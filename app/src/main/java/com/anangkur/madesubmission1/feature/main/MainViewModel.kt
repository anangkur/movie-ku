package com.anangkur.madesubmission1.feature.main

import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.data.Repository

class MainViewModel(private val repository: Repository) {

    val languageLive = MutableLiveData<String>()

    fun loadLanguageSetting(){
        languageLive.value = repository.loadLanguage()
    }
}