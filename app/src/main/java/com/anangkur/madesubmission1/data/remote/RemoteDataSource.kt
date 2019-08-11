package com.anangkur.madesubmission1.data.remote

import com.anangkur.madesubmission1.BuildConfig.apiKey
import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.model.Response
import com.anangkur.madesubmission1.data.model.Result
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object RemoteDataSource: DataSource{

    override fun saveAlarmState(alarmState: String, type: Int) {
        // do nothing
    }

    override fun loadAlarmState(type: Int): String? {
        return null
    }

    override fun deleteAlarmState(type: Int) {
        // do nothing
    }

    override fun saveFirebaseMessagingToken(token: String) {
        // do nothing
    }

    override fun loadFirebaseMessagingToken(): String? {
        // do nothing
        return null
    }

    override fun getSearchData(urlType: String, query: String, callback: DataSource.GetDataCallback) {
        ApiService.getApiService.getSearchData(urlType, apiKey, query)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onShowProgressDialog() }
            .subscribe(object : Observer<Response>{
                override fun onComplete() {
                    callback.onHideProgressDialog()
                }
                override fun onSubscribe(d: Disposable) {
                    callback.onSubscribe(d)
                }
                override fun onNext(t: Response) {
                    callback.onSuccess(t)
                }
                override fun onError(e: Throwable) {
                    callback.onFailed(e.message)
                }
            })
    }

    override fun getAllResult(callback: DataSource.ProviderCallback) {
        // do nothing
    }

    override fun getResultById(id: Int, callback: DataSource.ProviderCallback) {
        // do nothing
    }

    override fun bulkInsertResult(data: Result, callback: DataSource.ProviderCallback) {
        // do nothing
    }

    override fun deleteResult(data: Result, callback: DataSource.ProviderCallback) {
        // do nothing
    }

    override fun getData(page: Int, urlType: String, urlFilter: String,  callback: DataSource.GetDataCallback) {
        ApiService.getApiService.getData(urlType, urlFilter, apiKey, page)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onShowProgressDialog() }
            .subscribe(object : Observer<Response>{
                override fun onComplete() {
                    callback.onHideProgressDialog()
                }
                override fun onSubscribe(d: Disposable) {
                    callback.onSubscribe(d)
                }
                override fun onNext(t: Response) {
                    callback.onSuccess(t)
                }
                override fun onError(e: Throwable) {
                    callback.onFailed(e.message)
                }
            })
    }

    override fun saveLanguage(language: String) {
        // do nothing
    }
    override fun loadLanguage(): String? {
        // do nothing
        return null
    }
}