package com.anangkur.madesubmission1.data

import android.database.Cursor
import com.anangkur.madesubmission1.data.model.Response
import com.anangkur.madesubmission1.data.model.Result
import io.reactivex.disposables.Disposable

interface DataSource {
    // remote data
    fun getData(page: Int, urlType: String, urlFilter: String, callback: GetDataCallback)
    fun getSearchData(urlType: String, query: String, callback: GetDataCallback)

    // local data
    fun saveLanguage(language: String)
    fun loadLanguage(): String?
    fun bulkInsertResult(data: Result, callback: ProviderCallback)
    fun getAllResult(callback: ProviderCallback)
    fun deleteResult(data: Result, callback: ProviderCallback)
    fun getResultById(id: Int, callback: ProviderCallback)
    fun saveFirebaseMessagingToken(token: String)
    fun loadFirebaseMessagingToken(): String?
    fun saveAlarmState(alarmState: String, type: Int)
    fun loadAlarmState(type: Int): String?
    fun deleteAlarmState(type: Int)

    // response callback
    interface GetDataCallback: ResponseCallback<Response>

    interface ResponseCallback<T>{
        fun onShowProgressDialog()
        fun onHideProgressDialog()
        fun onSuccess(data: T)
        fun onFailed(errorMessage: String? = "")
        fun onSubscribe(disposable: Disposable)
    }
    interface ProviderCallback{
        fun onPreExcecute()
        fun onPostExcecute(data: Cursor?)
        fun onPostExcecute()
    }
}