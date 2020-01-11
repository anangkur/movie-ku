package com.anangkur.movieku.data

import android.database.Cursor
import android.net.Uri
import com.anangkur.movieku.data.local.LocalDataSource
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.data.remote.RemoteDataSource

open class Repository(val localDataSource: LocalDataSource, val remoteDataSource: RemoteDataSource): DataSource{

    override fun saveAlarmState(alarmState: String, type: Int) {
        localDataSource.saveAlarmState(alarmState, type)
    }

    override fun loadAlarmState(type: Int, callback: DataSource.PreferencesCallback){
        localDataSource.loadAlarmState(type, object: DataSource.PreferencesCallback{
            override fun onSuccess(data: String?) {
                callback.onSuccess(data)
            }
        })
    }

    override fun deleteAlarmState(type: Int) {
        localDataSource.deleteAlarmState(type)
    }

    override fun saveFirebaseMessagingToken(token: String) {
        localDataSource.saveFirebaseMessagingToken(token)
    }

    override fun loadFirebaseMessagingToken(callback: DataSource.PreferencesCallback){
        localDataSource.loadFirebaseMessagingToken(object: DataSource.PreferencesCallback{
            override fun onSuccess(data: String?) {
                callback.onSuccess(data)
            }
        })
    }

    override fun getSearchData(urlType: String, query: String, callback: DataSource.GetDataCallback){
        remoteDataSource.getSearchData(urlType, query, object: DataSource.GetDataCallback{
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
        })
    }

    override fun getAllResult(callback: DataSource.GetDataProviderCallback) {
        localDataSource.getAllResult(object: DataSource.GetDataProviderCallback{
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }
            override fun onHideProgressDialog() {
                callback.onHideProgressDialog()
            }
            override fun onSuccess(data: Cursor?) {
                callback.onSuccess(data)
            }
            override fun onFailed(errorMessage: String?) {
                callback.onFailed(errorMessage)
            }
        })
    }

    override fun getResultById(id: Int, callback: DataSource.GetDataProviderCallback) {
        localDataSource.getResultById(id, object: DataSource.GetDataProviderCallback{
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }
            override fun onHideProgressDialog() {
                callback.onHideProgressDialog()
            }
            override fun onSuccess(data: Cursor?) {
                callback.onSuccess(data)
            }
            override fun onFailed(errorMessage: String?) {
                callback.onFailed(errorMessage)
            }
        })
    }

    override fun bulkInsertResult(data: Result, callback: DataSource.PostDataProfiderCallback) {
        localDataSource.bulkInsertResult(data, object: DataSource.PostDataProfiderCallback{
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }
            override fun onHideProgressDialog() {
                callback.onHideProgressDialog()
            }
            override fun onSuccess(data: Uri?) {
                callback.onSuccess(data)
            }
            override fun onFailed(errorMessage: String?) {
                callback.onFailed(errorMessage)
            }
        })
    }

    override fun deleteResult(data: Result, callback: DataSource.DeleteDataProviderCallback) {
        localDataSource.deleteResult(data, object: DataSource.DeleteDataProviderCallback{
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }
            override fun onHideProgressDialog() {
                callback.onHideProgressDialog()
            }
            override fun onSuccess(data: Int) {
                callback.onSuccess(data)
            }
            override fun onFailed(errorMessage: String?) {
                callback.onFailed(errorMessage)
            }
        })
    }

    override fun getData(page: Int, urlType: String, urlFilter: String, callback: DataSource.GetDataCallback){
        remoteDataSource.getData(page, urlType, urlFilter, object: DataSource.GetDataCallback{
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
        })
    }

    override fun saveLanguage(language: String) {
        localDataSource.saveLanguage(language)
    }

    override fun loadLanguage(callback: DataSource.PreferencesCallback){
        localDataSource.loadLanguage(object: DataSource.PreferencesCallback{
            override fun onSuccess(data: String?) {
                callback.onSuccess(data)
            }
        })
    }
}