package com.anangkur.movieku.data

import android.database.Cursor
import android.net.Uri
import com.anangkur.movieku.data.model.Result

interface DataSource {
    // remote data
    fun getData(page: Int, urlType: String, urlFilter: String, callback: GetDataCallback){}
    fun getSearchData(urlType: String, query: String, callback: GetDataCallback){}

    // local data
    fun saveLanguage(language: String){}
    fun loadLanguage(callback: PreferencesCallback){}
    fun bulkInsertResult(data: Result, callback: PostDataProfiderCallback){}
    fun getAllResult(callback: GetDataProviderCallback){}
    fun deleteResult(data: Result, callback: DeleteDataProviderCallback){}
    fun getResultById(id: Int, callback: GetDataProviderCallback){}
    fun saveFirebaseMessagingToken(token: String){}
    fun loadFirebaseMessagingToken(callback: PreferencesCallback){}
    fun saveAlarmState(alarmState: String, type: Int){}
    fun loadAlarmState(type: Int, callback: PreferencesCallback){}
    fun deleteAlarmState(type: Int){}

    // response callback
    interface GetDataCallback: ResponseCallback<List<Result>>
    interface GetDataProviderCallback: ResponseCallback<Cursor?>
    interface PostDataProfiderCallback: ResponseCallback<Uri?>
    interface DeleteDataProviderCallback: ResponseCallback<Int>
    interface ResponseCallback<T>{
        fun onShowProgressDialog()
        fun onHideProgressDialog()
        fun onSuccess(data: T)
        fun onFailed(errorMessage: String? = "")
    }
    interface PreferencesCallback{
        fun onSuccess(data: String?)
    }
}