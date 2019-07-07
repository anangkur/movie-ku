package com.anangkur.madesubmission1.data.remote

import com.anangkur.madesubmission1.data.DataSource

object RemoteDataSource : DataSource{
    override fun saveLanguage(language: String) {
        // do nothing
    }
    override fun loadLanguage(): String? {
        // do nothing
        return null
    }
}