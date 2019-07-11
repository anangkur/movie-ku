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
    override fun getAllResult(callback: DataSource.GetResultRoomCallback, type: Int) {
        // do nothing
    }

    override fun getResultById(id: Int, callback: DataSource.GetResultByIdRoomCallback) {
        // do nothing
    }

    override fun bulkInsertResult(data: Result, callback: DataSource.RoomCallback) {
        // do nothing
    }

    override fun deleteResult(data: Result, callback: DataSource.RoomCallback) {
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
                    // do nothing
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