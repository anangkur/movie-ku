package com.anangkur.madesubmission1.data.local

import android.content.Context
import android.net.Uri
import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.utils.Const
import com.anangkur.madesubmission1.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LocalDataSource(private val preferenceHelper: SharedPreferenceHelper, private val context: Context): DataSource{

    override fun saveAlarmState(alarmState: String, type: Int) {
        when(type){
            Const.typeAlarmDaily -> preferenceHelper.saveStringPreferences(Const.PREF_ALARM_STATE_DAILY, alarmState)
            Const.typeAlarmRelease -> preferenceHelper.saveStringPreferences(Const.PREF_ALARM_STATE_RELEASE, alarmState)
        }
    }

    override fun loadAlarmState(type: Int, callback: DataSource.PreferencesCallback){
        when(type){
            Const.typeAlarmDaily -> callback.onSuccess(preferenceHelper.loadStringPreferences(Const.PREF_ALARM_STATE_DAILY))
            Const.typeAlarmRelease -> callback.onSuccess(preferenceHelper.loadStringPreferences(Const.PREF_ALARM_STATE_RELEASE))
        }
    }

    override fun deleteAlarmState(type: Int) {
        when (type){
            Const.typeAlarmDaily -> preferenceHelper.deleteStringPreferences(Const.PREF_ALARM_STATE_DAILY)
            Const.typeAlarmRelease -> preferenceHelper.deleteStringPreferences(Const.PREF_ALARM_STATE_RELEASE)
        }
    }

    override fun saveFirebaseMessagingToken(token: String) {
        preferenceHelper.saveStringPreferences(Const.PREF_FIREBASE_MESSAGING_TOKEN, token)
    }

    override fun loadFirebaseMessagingToken(callback: DataSource.PreferencesCallback){
        callback.onSuccess(preferenceHelper.loadStringPreferences(Const.PREF_FIREBASE_MESSAGING_TOKEN))
    }

    override fun getAllResult(callback: DataSource.GetDataProviderCallback) {
        callback.onShowProgressDialog()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = context.contentResolver.query(Const.URI_MOVIE, null, null, null, null)
                withContext(Dispatchers.Main){
                    callback.onSuccess(data)
                    callback.onHideProgressDialog()
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    callback.onFailed(e.message)
                    callback.onHideProgressDialog()
                }
            }
        }
    }

    override fun getResultById(id: Int, callback: DataSource.GetDataProviderCallback) {
        callback.onShowProgressDialog()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = context.contentResolver.query(Uri.parse("${Const.URI_MOVIE}/$id"), null, null, null, null)
                withContext(Dispatchers.Main){
                    callback.onSuccess(data)
                    callback.onHideProgressDialog()
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    callback.onFailed(e.message)
                    callback.onHideProgressDialog()
                }
            }
        }
    }

    override fun bulkInsertResult(data: Result, callback: DataSource.PostDataProfiderCallback) {
        callback.onShowProgressDialog()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = context.contentResolver.insert(Const.URI_MOVIE, Utils.convertResultToContentValue(data))
                withContext(Dispatchers.Main){
                    callback.onSuccess(data)
                    callback.onHideProgressDialog()
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    callback.onFailed(e.message)
                    callback.onHideProgressDialog()
                }
            }
        }
    }

    override fun deleteResult(data: Result, callback: DataSource.DeleteDataProviderCallback) {
        callback.onShowProgressDialog()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = context.contentResolver.delete(Uri.parse("${Const.URI_MOVIE}/${data.id}"), "${Const.COLUMN_ID}=${data.id}", null)
                withContext(Dispatchers.Main){
                    callback.onSuccess(data)
                    callback.onHideProgressDialog()
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    callback.onFailed(e.message)
                    callback.onHideProgressDialog()
                }
            }
        }
    }

    override fun saveLanguage(language: String) {
        preferenceHelper.saveStringPreferences(Const.PREF_LANGUAGE, language)
    }

    override fun loadLanguage(callback: DataSource.PreferencesCallback){
        callback.onSuccess(preferenceHelper.loadStringPreferences(Const.PREF_LANGUAGE))
    }
}