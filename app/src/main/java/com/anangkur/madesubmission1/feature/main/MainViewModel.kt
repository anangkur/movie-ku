package com.anangkur.madesubmission1.feature.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.BuildConfig.*
import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.model.Result

class MainViewModel(application: Application, val repository: Repository): AndroidViewModel(application) {

    val sliderDataLive = MutableLiveData<List<Result>>()
    val showProgressSliderLive = MutableLiveData<Boolean>()
    val showErrorSliderLive = MutableLiveData<String>()

    val horizontalDataLive = MutableLiveData<List<Result>>()
    val showProgressHorizontalLive = MutableLiveData<Boolean>()
    val showErrorHorizontalLive = MutableLiveData<String>()

    val verticalLiveData = MutableLiveData<List<Result>>()
    val showProgressVerticalLive = MutableLiveData<Boolean>()
    val showErrorVerticalLive = MutableLiveData<String>()

    val tvPopularLive = MutableLiveData<List<Result>>()
    val tvNewLive = MutableLiveData<List<Result>>()
    val tvRatingLive = MutableLiveData<List<Result>>()

    val showErrorPopular = MutableLiveData<String>()
    val showErrorNew = MutableLiveData<String>()
    val showErrorRating = MutableLiveData<String>()

    val showProgressPopular = MutableLiveData<Boolean>()
    val showProgressNew = MutableLiveData<Boolean>()
    val showProgressRating = MutableLiveData<Boolean>()

    val firebaseToken = MutableLiveData<String>()

    fun getTvPopular(page: Int){
        repository.getData(page, tvUrl, popularUrl, object: DataSource.GetDataCallback{
            override fun onShowProgressDialog() {
                showProgressPopular.value = true
            }
            override fun onHideProgressDialog() {
                showProgressPopular.value = false
            }
            override fun onSuccess(data: List<Result>) {
                tvPopularLive.value = data
            }
            override fun onFailed(errorMessage: String?) {
                showErrorPopular.value = errorMessage
            }
        })
    }

    fun getTvNew(page: Int){
        repository.getData(page, tvUrl, onTheAirUrl, object: DataSource.GetDataCallback{
            override fun onShowProgressDialog() {
                showProgressNew.value = true
            }
            override fun onHideProgressDialog() {
                showProgressNew.value = false
            }
            override fun onSuccess(data: List<Result>) {
                tvNewLive.value = data
            }
            override fun onFailed(errorMessage: String?) {
                showErrorNew.value = errorMessage
            }
        })
    }

    fun getTvRating(page: Int){
        repository.getData(page, tvUrl, topRatedUrl, object: DataSource.GetDataCallback{
            override fun onShowProgressDialog() {
                showProgressRating.value = true
            }
            override fun onHideProgressDialog() {
                showProgressRating.value = false
            }
            override fun onSuccess(data: List<Result>) {
                tvRatingLive.value = data
            }
            override fun onFailed(errorMessage: String?) {
                showErrorRating.value = errorMessage
            }
        })
    }

    fun getHorizontalData(page: Int){
        repository.getData(page, movieUrl, nowPlayingUrl, object: DataSource.GetDataCallback{
            override fun onShowProgressDialog() {
                showProgressHorizontalLive.value = true
            }
            override fun onHideProgressDialog() {
                showProgressHorizontalLive.value = false
            }
            override fun onSuccess(data: List<Result>) {
                horizontalDataLive.value = data
            }
            override fun onFailed(errorMessage: String?) {
                showErrorHorizontalLive.value = errorMessage
            }
        })
    }

    fun getVerticalData(page: Int){
        repository.getData(page, movieUrl, popularUrl, object: DataSource.GetDataCallback{
            override fun onShowProgressDialog() {
                showProgressVerticalLive.value = true
            }
            override fun onHideProgressDialog() {
                showProgressVerticalLive.value = false
            }
            override fun onSuccess(data: List<Result>) {
                verticalLiveData.value = data
            }
            override fun onFailed(errorMessage: String?) {
                showErrorVerticalLive.value = errorMessage
            }
        })
    }

    fun getSliderData(page: Int){
        repository.getData(page, movieUrl, nowPlayingUrl, object: DataSource.GetDataCallback{
            override fun onShowProgressDialog() {
                showProgressSliderLive.value = true
            }
            override fun onHideProgressDialog() {
                showProgressSliderLive.value = false
            }
            override fun onSuccess(data: List<Result>) {
                sliderDataLive.value = data
            }
            override fun onFailed(errorMessage: String?) {
                showErrorSliderLive.value = errorMessage
            }
        })
    }

    fun saveFirebaseMessagingToken(token: String){
        repository.saveFirebaseMessagingToken(token)
    }

    fun loadFirebaseMessagingToken(){
        repository.loadFirebaseMessagingToken(object: DataSource.PreferencesCallback{
            override fun onSuccess(data: String?) {
                firebaseToken.value = data
            }
        })
    }
}