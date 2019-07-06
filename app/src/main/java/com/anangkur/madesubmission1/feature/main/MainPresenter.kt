package com.anangkur.madesubmission1.feature.main

import android.app.Activity
import com.anangkur.madesubmission1.data.local.Preferences

class MainPresenter(private val view: MainView) {
    fun loadLanguageSetting(activity: Activity){
        view.onLanguageReady(Preferences.loadLanguage(activity))
    }
}