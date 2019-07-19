package com.anangkur.madesubmission1.data

import android.database.Cursor
import com.anangkur.madesubmission1.data.local.LocalDataSource
import com.anangkur.madesubmission1.data.model.Response
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.data.remote.RemoteDataSource

class Repository(private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource): DataSource{
    override fun saveAlarmState(alarmState: String, type: Int) {
        localDataSource.saveAlarmState(alarmState, type)
    }

    override fun loadAlarmState(type: Int): String? {
        return localDataSource.loadAlarmState(type)
    }

    override fun deleteAlarmState(type: Int) {
        localDataSource.deleteAlarmState(type)
    }

    override fun saveFirebaseMessagingToken(token: String) {
        localDataSource.saveFirebaseMessagingToken(token)
    }

    override fun getSearchData(urlType: String, query: String, callback: DataSource.GetDataCallback) {
        remoteDataSource.getSearchData(urlType, query, object : DataSource.GetDataCallback{
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

    override fun getAllResult(callback: DataSource.ProviderCallback) {
        localDataSource.getAllResult(object : DataSource.ProviderCallback{
            override fun onPostExcecute() {
                callback.onPostExcecute()
            }

            override fun onPreExcecute() {
                callback.onPreExcecute()
            }
            override fun onPostExcecute(data: Cursor?) {
                callback.onPostExcecute(data)
            }
        })
    }

    override fun getResultById(id: Int, callback: DataSource.ProviderCallback) {
        localDataSource.getResultById(id, object : DataSource.ProviderCallback{
            override fun onPostExcecute() {
                callback.onPostExcecute()
            }

            override fun onPreExcecute() {
                callback.onPreExcecute()
            }
            override fun onPostExcecute(data: Cursor?) {
                callback.onPostExcecute(data)
            }
        })
    }

    override fun bulkInsertResult(data: Result, callback: DataSource.ProviderCallback) {
        localDataSource.bulkInsertResult(data, object : DataSource.ProviderCallback{
            override fun onPreExcecute() {
                callback.onPreExcecute()
            }
            override fun onPostExcecute(data: Cursor?) {
                callback.onPostExcecute(data)
            }
            override fun onPostExcecute() {
                callback.onPostExcecute()
            }
        })
    }

    override fun deleteResult(data: Result, callback: DataSource.ProviderCallback) {
        localDataSource.deleteResult(data, object : DataSource.ProviderCallback{
            override fun onPreExcecute() {
                callback.onPreExcecute()
            }
            override fun onPostExcecute(data: Cursor?) {
                callback.onPostExcecute(data)
            }
            override fun onPostExcecute() {
                callback.onPostExcecute()
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