package com.anangkur.madesubmission1.data.local

import android.content.Context

class SharedPreferenceHelper(context: Context){

    private val prefName = "com.anangkur.madesubmission"
    private val sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun saveStringPreferences(key: String, language: String){
        sharedPreferences.edit().putString(key, language).apply()
    }

    fun loadStringPreferences(key: String): String?{
        return sharedPreferences.getString(key, "")
    }
}