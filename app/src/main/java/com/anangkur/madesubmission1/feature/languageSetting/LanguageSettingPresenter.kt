package com.anangkur.madesubmission1.feature.languageSetting

import com.anangkur.madesubmission1.data.local.SharedPreferenceHelper

class LanguageSettingPresenter(private val view: LanguageSettingView, private val sharedPreferenceHelper: SharedPreferenceHelper) {
    fun loadLanguageSetting(){
        view.onLanguageReady(sharedPreferenceHelper.loadLanguage())
    }

    fun saveLanguageSetting(language: String){
        sharedPreferenceHelper.saveLanguage(language)
        view.onLanguageReady(language)
    }
}