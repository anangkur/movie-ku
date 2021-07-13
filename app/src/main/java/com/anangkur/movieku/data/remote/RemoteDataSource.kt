package com.anangkur.movieku.data.remote

import com.anangkur.movieku.BuildConfig.apiKey
import com.anangkur.movieku.data.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService): DataSource{

    override fun getSearchData(urlType: String, query: String, callback: DataSource.GetDataCallback){
        callback.onShowProgressDialog()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getSearchData(urlType, apiKey, query)
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
                val response = apiService.getData(urlType, urlFilter, apiKey, page)
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