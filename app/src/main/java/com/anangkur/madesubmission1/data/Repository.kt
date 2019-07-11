package com.anangkur.madesubmission1.data

import com.anangkur.madesubmission1.data.local.LocalDataSource
import com.anangkur.madesubmission1.data.model.Response
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.data.remote.RemoteDataSource

class Repository(private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource): DataSource{
    override fun getAllResult(callback: DataSource.GetResultRoomCallback, type: Int) {
        localDataSource.getAllResult(object : DataSource.GetResultRoomCallback{
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }
            override fun onHideProgressDialog() {
                callback.onHideProgressDialog()
            }
            override fun onSuccess(data: List<Result>) {
                callback.onSuccess(data)
            }
            override fun onFailed(errorMessage: String?) {
                callback.onFailed(errorMessage)
            }
        }, type)
    }

    override fun getResultById(id: Int, callback: DataSource.GetResultByIdRoomCallback) {
        localDataSource.getResultById(id, object : DataSource.GetResultByIdRoomCallback{
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }
            override fun onHideProgressDialog() {
                callback.onHideProgressDialog()
            }
            override fun onSuccess(data: Result) {
                callback.onSuccess(data)
            }
            override fun onFailed(errorMessage: String?) {
                callback.onFailed(errorMessage)
            }
        })
    }

    override fun bulkInsertResult(data: Result, callback: DataSource.RoomCallback) {
        localDataSource.bulkInsertResult(data, object : DataSource.RoomCallback{
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }
            override fun onHideProgressDialog() {
                callback.onHideProgressDialog()
            }
            override fun onSuccess() {
                callback.onSuccess()
            }
            override fun onFailed(errorMessage: String?) {
                callback.onFailed(errorMessage)
            }
        })
    }

    override fun deleteResult(data: Result, callback: DataSource.RoomCallback) {
        localDataSource.deleteResult(data, object : DataSource.RoomCallback{
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }
            override fun onHideProgressDialog() {
                callback.onHideProgressDialog()
            }
            override fun onSuccess() {
                callback.onSuccess()
            }
            override fun onFailed(errorMessage: String?) {
                callback.onFailed(errorMessage)
            }
        })
    }

    override fun getData(page: Int, urlType: String, urlFilter: String,  callback: DataSource.GetDataCallback) {
        remoteDataSource.getData(page, urlType, urlFilter, object : DataSource.GetDataCallback{
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }
            override fun onHideProgressDialog() {
                callback.onHideProgressDialog()
            }
            override fun onSuccess(data: Response) {
                callback.onSuccess(data)
            }
            override fun onFailed(errorMessage: String?) {
                callback.onFailed(errorMessage)
            }
        })
    }

    override fun saveLanguage(language: String) {
        localDataSource.saveLanguage(language)
    }

    override fun loadLanguage(): String? {
        return localDataSource.loadLanguage()
    }
}