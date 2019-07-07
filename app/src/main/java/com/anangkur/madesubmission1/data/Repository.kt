package com.anangkur.madesubmission1.data

import com.anangkur.madesubmission1.data.local.LocalDataSource
import com.anangkur.madesubmission1.data.remote.RemoteDataSource

class Repository(private val localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource): DataSource{
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