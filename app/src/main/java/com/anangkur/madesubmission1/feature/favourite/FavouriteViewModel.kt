package com.anangkur.madesubmission1.feature.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.utils.Const

class FavouriteViewModel(application: Application, private val repository: Repository): AndroidViewModel(application){

    val movieLive = MutableLiveData<List<Result>>()
    val showProgressGetMovie = MutableLiveData<Boolean>()
    val showErrorGetMovie = MutableLiveData<String>()

    val tvLive = MutableLiveData<List<Result>>()
    val showProgressGetTv = MutableLiveData<Boolean>()
    val showErrorGetTv = MutableLiveData<String>()

    fun getAllMovie(){
        repository.getAllResult(object : DataSource.GetResultRoomCallback{
            override fun onShowProgressDialog() {
                showProgressGetMovie.value = true
            }
            override fun onHideProgressDialog() {
                showProgressGetMovie.value = false
            }
            override fun onSuccess(data: List<Result>) {
                movieLive.value = data
                onHideProgressDialog()
            }
            override fun onFailed(errorMessage: String?) {
                showErrorGetMovie.value = errorMessage
                onHideProgressDialog()
            }
        }, Const.TYPE_MOVIE)
    }

    fun getAllTv(){
        repository.getAllResult(object : DataSource.GetResultRoomCallback{
            override fun onShowProgressDialog() {
                showProgressGetTv.value = true
            }
            override fun onHideProgressDialog() {
                showProgressGetTv.value = false
            }
            override fun onSuccess(data: List<Result>) {
                tvLive.value = data
                onHideProgressDialog()
            }
            override fun onFailed(errorMessage: String?) {
                showErrorGetTv.value = errorMessage
                onHideProgressDialog()
            }
        }, Const.TYPE_TV)
    }
}