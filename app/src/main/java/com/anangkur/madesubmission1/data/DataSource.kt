package com.anangkur.madesubmission1.data

import android.database.Cursor
import com.anangkur.madesubmission1.data.model.Response
import com.anangkur.madesubmission1.data.model.Result

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
    fun saveAlarmState(alarmState: String, type: Int)
    fun loadAlarmState(type: Int): String?
    fun deleteAlarmState(type: Int)

    // response callback
    interface GetDataCallback: ResponseCallback<Response>
    interface GetResultRoomCallback: ResponseCallback<List<Result>>
    interface GetResultByIdRoomCallback: ResponseCallback<Result>

    interface ResponseCallback<T>{
        fun onShowProgressDialog()
        fun onHideProgressDialog()
        fun onSuccess(data: T)
        fun onFailed(errorMessage: String? = "")
    }
    interface RoomCallback{
        fun onShowProgressDialog()
        fun onHideProgressDialog()
        fun onSuccess()
        fun onFailed(errorMessage: String? = "")
    }
    interface ProviderCallback{
        fun onPreExcecute()
        fun onPostExcecute(data: Cursor?)
        fun onPostExcecute()
    }
}