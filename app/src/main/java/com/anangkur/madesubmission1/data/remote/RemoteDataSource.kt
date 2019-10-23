package com.anangkur.madesubmission1.data.remote

import com.anangkur.madesubmission1.BuildConfig.apiKey
import com.anangkur.madesubmission1.data.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

object RemoteDataSource: DataSource{

    override fun getSearchData(urlType: String, query: String, callback: DataSource.GetDataCallback){
        callback.onShowProgressDialog()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiService.getApiService.getSearchData(urlType, apiKey, query)
                withContext(Dispatchers.Main){
                    if (response.results.isNotEmpty()){
                        callback.onSuccess(response.results)
                    }else{
                        callback.onFailed(response.status_message)
                    }
                    callback.onHideProgressDialog()
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    callback.onFailed(e.message)
                    callback.onHideProgressDialog()
                }
            }
        }
    }

    override fun getData(page: Int, urlType: String, urlFilter: String, callback: DataSource.GetDataCallback){
        callback.onShowProgressDialog()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiService.getApiService.getData(urlType, urlFilter, apiKey, page)
                withContext(Dispatchers.Main){
                    if (response.results.isNotEmpty()){
                        callback.onSuccess(response.results)
                    }else{
                        callback.onFailed(response.status_message)
                    }
                    callback.onHideProgressDialog()
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    callback.onFailed(e.message)
                    callback.onHideProgressDialog()
                }
            }
        }
    }
}