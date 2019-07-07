package com.anangkur.madesubmission1.feature.main

import com.anangkur.madesubmission1.data.local.SharedPreferenceHelper

class MainPresenter(private val view: MainView, private val sharedPreferenceHelper: SharedPreferenceHelper) {
    fun loadLanguageSetting(){
        view.onLanguageReady(sharedPreferenceHelper.loadLanguage())
    }
}