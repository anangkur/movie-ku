package com.anangkur.madesubmission1.data

import com.anangkur.madesubmission1.data.model.Response

interface DataSource {
    // remote data
    fun getData(page: Int, urlType: String, urlFilter: String, callback: GetDataCallback)

    // local data
    fun saveLanguage(language: String)
    fun loadLanguage(): String?

    // response callback
    interface GetDataCallback: ResponseCallback<Response>
}