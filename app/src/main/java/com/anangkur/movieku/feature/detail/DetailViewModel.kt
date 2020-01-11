package com.anangkur.movieku.feature.detail

import android.app.Application
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anangkur.movieku.data.DataSource
import com.anangkur.movieku.data.Repository
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.utils.Utils

class DetailViewModel(application: Application, private val repository: Repository): AndroidViewModel(application){

    val resultLive = MutableLiveData<Result>()
    lateinit var result: Result

    val showProgressInsertResult = MutableLiveData<Boolean>()
    val successInsertResult = MutableLiveData<Boolean>()

    val showProgressDeleteResult = MutableLiveData<Boolean>()
    val successDeleteResult = MutableLiveData<Boolean>()

    val showProgressGetData = MutableLiveData<Boolean>()
    val successGetData = MutableLiveData<Result>()

    fun getDataFromIntent(data: Result, type: Int){
        resultLive.value = data.copy(type = type)
    }

    fun deleteData(data: Result){
        repository.deleteResult(data, object: DataSource.DeleteDataProviderCallback{
            override fun onShowProgressDialog() {
                showProgressDeleteResult.value = true
            }
            override fun onHideProgressDialog() {
                showProgressDeleteResult.value = false
            }
            override fun onSuccess(data: Int) {
                successDeleteResult.value = true
            }
            override fun onFailed(errorMessage: String?) {
                // do nothing
            }
        })
        result = data
    }

    fun bulkInsertData(data: Result){
        repository.bulkInsertResult(data, object: DataSource.PostDataProfiderCallback{
            override fun onShowProgressDialog() {
                showProgressInsertResult.value = true
            }
            override fun onHideProgressDialog() {
                showProgressInsertResult.value = false
            }
            override fun onSuccess(data: Uri?) {
                successInsertResult.value = true
            }
            override fun onFailed(errorMessage: String?) {
                // do nothing
            }
        })
    }

    fun getDataById(result: Result){
        repository.getResultById(result.id, object: DataSource.GetDataProviderCallback{
            override fun onShowProgressDialog() {
                showProgressGetData.value = false
            }
            override fun onHideProgressDialog() {
                showProgressGetData.value = true
            }
            override fun onSuccess(data: Cursor?) {
                if (data != null){
                    successGetData.value = Utils.convertCursorIntoResult(data)
                }
            }
            override fun onFailed(errorMessage: String?) {
                // do nothing
            }
        })
    }
}