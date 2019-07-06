package com.anangkur.madesubmission1.data.local

import android.app.Activity
import android.content.Context
import android.util.Log
import com.anangkur.madesubmission1.utils.Const

object Preferences{

    fun saveLanguage(activity: Activity, language: String){
        Log.d("LANGUAGE", language)
        saveStringPreferences(activity, Const.PREF_LANGUAGE, language)
    }

    fun loadLanguage(activity: Activity): String?{
        val language = loadStringPreferences(activity, Const.PREF_LANGUAGE)
        Log.d("LANGUAGE", language?:"empty")
        return language
    }

    fun deleteLanguage(activity: Activity){
        deletePreferences(activity, Const.PREF_LANGUAGE)
    }

    private fun saveStringPreferences(activity: Activity, key: String, language: String){
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit().putString(key, language).apply()
    }

    private fun loadStringPreferences(activity: Activity, key: String): String?{
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getString(key, "")
    }

    private fun deletePreferences(activity: Activity, key: String){
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit().remove(key).apply()
    }
}