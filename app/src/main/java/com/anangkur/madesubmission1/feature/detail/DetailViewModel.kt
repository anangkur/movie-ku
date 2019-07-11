package com.anangkur.madesubmission1.feature.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.utils.Const

class DetailViewModel(application: Application, private val repository: Repository): AndroidViewModel(application){

    val resultLive = MutableLiveData<Result>()

    val showProgressInsertResult = MutableLiveData<Boolean>()
    val showErrorInsertResult = MutableLiveData<String>()
    val successInsertResult = MutableLiveData<String>()

    val showProgressDeleteResult = MutableLiveData<Boolean>()
    val showErrorDeleteResult = MutableLiveData<String>()
    val successDeleteResult = MutableLiveData<String>()

    val showProgressGetData = MutableLiveData<Boolean>()
    val showErrorGetData = MutableLiveData<String>()
    val successGetData = MutableLiveData<Result>()

    fun getDataFromIntent(data: Result, type: Int){
        resultLive.value = data.copy(type = type)
    }

    fun deleteData(data: Result){
        repository.deleteResult(data, object : DataSource.RoomCallback{
            override fun onShowProgressDialog() {
                showProgressDeleteResult.value = true
            }
            override fun onHideProgressDialog() {
                showProgressDeleteResult.value = false
            }
            override fun onSuccess() {
                successDeleteResult.value = Const.SNACKBAR_DELETE_SUCCESS
            }
            override fun onFailed(errorMessage: String?) {
                showErrorDeleteResult.value = errorMessage
            }
        })
    }

    fun bulkInsertData(data: Result){
        repository.bulkInsertResult(data, object : DataSource.RoomCallback{
            override fun onShowProgressDialog() {
                showProgressInsertResult.value = true
            }
            override fun onHideProgressDialog() {
                showProgressInsertResult.value = false
            }
            override fun onSuccess() {
                successInsertResult.value = Const.SNACKBAR_INSERT_SUCCESS
            }
            override fun onFailed(errorMessage: String?) {
                showErrorInsertResult.value = errorMessage
            }
        })
    }

    fun getDataById(id: Int){
        repository.getResultById(id, object : DataSource.GetResultByIdRoomCallback{
            override fun onShowProgressDialog() {
                showProgressGetData.value = true
            }
            override fun onHideProgressDialog() {
                showProgressGetData.value = false
            }
            override fun onSuccess(data: Result) {
                successGetData.value = data
            }
            override fun onFailed(errorMessage: String?) {
                showErrorGetData.value = errorMessage
            }
        })
    }
}