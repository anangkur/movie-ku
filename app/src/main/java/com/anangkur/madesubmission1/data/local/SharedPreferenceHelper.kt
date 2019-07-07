package com.anangkur.madesubmission1.data.local

import android.content.Context
import com.anangkur.madesubmission1.utils.Const

class SharedPreferenceHelper(context: Context){

    private val prefName = "com.anangkur.madesubmission"
    private val sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun saveLanguage(language: String){
        saveStringPreferences(Const.PREF_LANGUAGE, language)
    }

    fun loadLanguage(): String?{
        val language = loadStringPreferences(Const.PREF_LANGUAGE)
        return language
    }

    private fun saveStringPreferences(key: String, language: String){
        sharedPreferences.edit().putString(key, language).apply()
    }

    private fun loadStringPreferences(key: String): String?{
        return sharedPreferences.getString(key, "")
    }
}