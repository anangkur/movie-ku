package com.anangkur.madesubmission1.feature.languageSetting

import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.data.Repository

class LanguageSettingViewModel(private val repository: Repository){

    val languageLive = MutableLiveData<String>()

    fun loadLanguageSetting(){
        languageLive.value = repository.loadLanguage()
    }

    fun saveLanguageSetting(language: String){
        repository.saveLanguage(language)
        languageLive.value = language
    }
}