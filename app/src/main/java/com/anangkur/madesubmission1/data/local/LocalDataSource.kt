package com.anangkur.madesubmission1.data.local

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.utils.Const
import com.anangkur.madesubmission1.utils.Utils

class LocalDataSource(private val preferenceHelper: SharedPreferenceHelper, private val context: Context): DataSource{
    override fun saveAlarmState(alarmState: String, type: Int) {
        when(type){
            Const.typeAlarmDaily -> preferenceHelper.saveStringPreferences(Const.PREF_ALARM_STATE_DAILY, alarmState)
            Const.typeAlarmRelease -> preferenceHelper.saveStringPreferences(Const.PREF_ALARM_STATE_RELEASE, alarmState)
        }
    }

    override fun loadAlarmState(type: Int): String? {
        return when(type){
            Const.typeAlarmDaily -> preferenceHelper.loadStringPreferences(Const.PREF_ALARM_STATE_DAILY)
            Const.typeAlarmRelease -> preferenceHelper.loadStringPreferences(Const.PREF_ALARM_STATE_RELEASE)
            else -> return null
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

    override fun getSearchData(urlType: String, query: String, callback: DataSource.GetDataCallback) {
        // do nothing
    }

    override fun getAllResult(callback: DataSource.ProviderCallback) {
        class Task : AsyncTask<Void, Void, Cursor>(){
            override fun doInBackground(vararg p0: Void?): Cursor? {
                return context.contentResolver.query(Const.URI_MOVIE, null, null, null, null)
            }
            override fun onPostExecute(result: Cursor?) {
                callback.onPostExcecute(result)
            }
            override fun onPreExecute() {
                callback.onPreExcecute()
            }
        }
        Task().execute()
    }

    override fun getResultById(id: Int, callback: DataSource.ProviderCallback) {
        class Task : AsyncTask<Void, Void, Cursor>(){
            override fun doInBackground(vararg p0: Void?): Cursor? {
                return context.contentResolver.query(Uri.parse("${Const.URI_MOVIE}/$id"), null, null, null, null)
            }
            override fun onPostExecute(result: Cursor?) {
                callback.onPostExcecute(result)
            }
            override fun onPreExecute() {
                callback.onPreExcecute()
            }
        }
        Task().execute()
    }

    override fun bulkInsertResult(data: Result, callback: DataSource.ProviderCallback) {
        class Task : AsyncTask<Void, Void, Uri>(){
            override fun doInBackground(vararg p0: Void?): Uri? {
                return context.contentResolver.insert(Const.URI_MOVIE, Utils.convertResultToContentValue(data))
            }
            override fun onPostExecute(result: Uri?) {
                callback.onPostExcecute()
            }
            override fun onPreExecute() {
                callback.onPreExcecute()
            }
        }
        Task().execute()
    }

    override fun deleteResult(data: Result, callback: DataSource.ProviderCallback) {
        class Task : AsyncTask<Void, Void, Int>(){
            override fun doInBackground(vararg p0: Void?): Int? {
                return context.contentResolver.delete(Uri.parse("${Const.URI_MOVIE}/${data.id}"), "${Const.COLUMN_ID}=${data.id}", null)
            }
            override fun onPostExecute(result: Int?) {
                callback.onPostExcecute()
            }
            override fun onPreExecute() {
                callback.onPreExcecute()
            }
        }
        Task().execute()
    }

    override fun getData(page: Int, urlType: String, urlFilter: String, callback: DataSource.GetDataCallback) {
        // do nothing
    }

    override fun saveLanguage(language: String) {
        preferenceHelper.saveStringPreferences(Const.PREF_LANGUAGE, language)
    }

    override fun loadLanguage(): String? {
        return preferenceHelper.loadStringPreferences(Const.PREF_LANGUAGE)
    }

}