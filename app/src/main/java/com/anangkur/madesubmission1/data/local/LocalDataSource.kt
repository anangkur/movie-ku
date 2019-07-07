package com.anangkur.madesubmission1.data.local

import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.utils.Const

class LocalDataSource (private val preferenceHelper: SharedPreferenceHelper): DataSource{
    override fun saveLanguage(language: String) {
        preferenceHelper.saveStringPreferences(Const.PREF_LANGUAGE, language)
    }

    override fun loadLanguage(): String? {
        return preferenceHelper.loadStringPreferences(Const.PREF_LANGUAGE)
    }

}