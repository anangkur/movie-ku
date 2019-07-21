package com.anangkur.madesubmission1.feature.detail

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.utils.Const
import com.anangkur.madesubmission1.utils.Utils

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
        Log.d("INTENT_DATA", "data: ${data.title?:data.name}, type: $type")
        resultLive.value = data.copy(type = type)
    }

    fun deleteData(data: Result){
        repository.deleteResult(data, object : DataSource.ProviderCallback{
            override fun onPreExcecute() {
                showProgressDeleteResult.value = true
            }
            override fun onPostExcecute(data: Cursor?) {
                showProgressDeleteResult.value = false
            }
            override fun onPostExcecute() {
                showProgressDeleteResult.value = false
                successDeleteResult.value = true
            }
        })
        result = data
    }

    fun bulkInsertData(data: Result){
        repository.bulkInsertResult(data, object : DataSource.ProviderCallback{
            override fun onPreExcecute() {
                showProgressInsertResult.value = true
            }
            override fun onPostExcecute(data: Cursor?) {
                showProgressInsertResult.value = false
            }
            override fun onPostExcecute() {
                showProgressInsertResult.value = false
                successInsertResult.value = true
            }
        })
    }

    fun getDataById(id: Int){
        Log.d("GET_DATA", "Id: $id")
        repository.getResultById(id, object : DataSource.ProviderCallback{
            override fun onPostExcecute() {
                showProgressGetData.value = false
            }

            override fun onPreExcecute() {
                showProgressGetData.value = true
            }
            override fun onPostExcecute(data: Cursor?) {
                showProgressGetData.value = false
                if (data != null){
                    successGetData.value = Utils.convertCursorIntoResult(data)
                }
            }
        })
    }
}