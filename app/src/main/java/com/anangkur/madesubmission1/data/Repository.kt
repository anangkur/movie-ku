package com.anangkur.madesubmission1.data

import com.anangkur.madesubmission1.data.local.LocalDataSource
import com.anangkur.madesubmission1.data.model.Response
import com.anangkur.madesubmission1.data.remote.RemoteDataSource

class Repository(private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource): DataSource{
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

    companion object {

        private var INSTANCE: Repository? = null

        @JvmStatic
        fun getInstance(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource) =
            INSTANCE ?: synchronized(Repository::class.java) {
                INSTANCE ?: Repository(localDataSource, remoteDataSource)
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}