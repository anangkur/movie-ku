package com.anangkur.madesubmission1.feature.languageSetting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.data.Repository

class LanguageSettingViewModel(application: Application, private val repository: Repository): AndroidViewModel(application){

    val languageLive = MutableLiveData<String>()

    fun loadLanguageSetting(){
        languageLive.value = repository.loadLanguage()
    }

    fun saveLanguageSetting(language: String){
        repository.saveLanguage(language)
        languageLive.value = language
    }
}