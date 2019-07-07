package com.anangkur.madesubmission1.feature.detail

import com.anangkur.madesubmission1.data.local.SharedPreferenceHelper
import com.anangkur.madesubmission1.data.model.Result

class DetailPresenter(private val view: DetailView, private val sharedPreferenceHelper: SharedPreferenceHelper){

    fun getDataFromIntent(data: Result){
        view.onDataReady(data)
    }

    fun loadLanguageSetting(){
        view.onLanguageReady(sharedPreferenceHelper.loadLanguage())
    }
}