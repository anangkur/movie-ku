package com.anangkur.madesubmission1.feature.main.languageSetting

import android.app.Activity
import com.anangkur.madesubmission1.data.local.Preferences

class LanguageSettingPresenter(private val view: LanguageSettingView) {
    fun loadLanguageSetting(activity: Activity){
        view.onLanguageReady(Preferences.loadLanguage(activity))
    }

    fun saveLanguageSetting(activity: Activity, language: String){
        Preferences.saveLanguage(activity, language)
        view.onLanguageReady(language)
    }
}