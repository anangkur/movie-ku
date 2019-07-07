package com.anangkur.madesubmission1.data.remote

import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.model.Response
import com.anangkur.madesubmission1.utils.Const
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object RemoteDataSource : DataSource{
    override fun getData(page: Int, urlType: String, urlFilter: String,  callback: DataSource.GetDataCallback) {
        ApiService.getApiService.getData(urlType, urlFilter, Const.apiKey, page)
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