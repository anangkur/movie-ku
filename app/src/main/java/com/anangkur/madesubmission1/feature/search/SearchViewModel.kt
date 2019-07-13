package com.anangkur.madesubmission1.feature.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.BuildConfig.*
import com.anangkur.madesubmission1.data.model.Response

class SearchViewModel(application: Application, private val repository: Repository): AndroidViewModel(application){
    val movieLive = MutableLiveData<List<Result>>()
    val showProgressGetMovie = MutableLiveData<Boolean>()
    val showErrorGetMovie = MutableLiveData<String>()

    val tvLive = MutableLiveData<List<Result>>()
    val showProgressGetTv = MutableLiveData<Boolean>()
    val showErrorGetTv = MutableLiveData<String>()

    fun getAllMovie(query: String){
        repository.getSearchData(movieUrl, query, object : DataSource.GetDataCallback{
            override fun onShowProgressDialog() {
                showProgressGetMovie.value = true
            }
            override fun onHideProgressDialog() {
                showProgressGetMovie.value = false
            }
            override fun onSuccess(data: Response) {
                movieLive.value = data.results
                onHideProgressDialog()
            }
            override fun onFailed(errorMessage: String?) {
                showErrorGetMovie.value = errorMessage
                onHideProgressDialog()
            }
        })
    }

    fun getAllTv(query: String){
        repository.getSearchData(tvUrl, query, object : DataSource.GetDataCallback{
            override fun onShowProgressDialog() {
                showProgressGetTv.value = true
            }
            override fun onHideProgressDialog() {
                showProgressGetTv.value = false
            }
            override fun onSuccess(data: Response) {
                tvLive.value = data.results
                onHideProgressDialog()
            }
            override fun onFailed(errorMessage: String?) {
                showErrorGetTv.value = errorMessage
                onHideProgressDialog()
            }
        })
    }
}