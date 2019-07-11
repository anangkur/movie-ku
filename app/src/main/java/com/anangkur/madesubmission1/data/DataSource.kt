package com.anangkur.madesubmission1.data

import com.anangkur.madesubmission1.data.model.Response
import com.anangkur.madesubmission1.data.model.Result

interface DataSource {
    // remote data
    fun getData(page: Int, urlType: String, urlFilter: String, callback: GetDataCallback)

    // local data
    fun saveLanguage(language: String)
    fun loadLanguage(): String?
    fun bulkInsertResult(data: Result, callback: RoomCallback)
    fun getAllResult(callback: GetResultRoomCallback, type: Int)
    fun deleteResult(data: Result, callback: RoomCallback)
    fun getResultById(id: Int, callback: GetResultByIdRoomCallback)

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
}