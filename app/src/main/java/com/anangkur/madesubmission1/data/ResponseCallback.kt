package com.anangkur.madesubmission1.data

interface ResponseCallback<T>{
    fun onShowProgressDialog()
    fun onHideProgressDialog()
    fun onSuccess(data: T)
    fun onFailed(errorMessage: String? = "")
}