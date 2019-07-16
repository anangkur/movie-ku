package com.anangkur.madesubmission1.data.local

import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.local.room.ResultDao
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.utils.Const
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LocalDataSource(private val preferenceHelper: SharedPreferenceHelper, private val resultDao: ResultDao): DataSource{
    override fun saveAlarmState(alarmState: String, type: Int) {
        when(type){
            Const.typeAlarmDaily -> preferenceHelper.saveStringPreferences(Const.PREF_ALARM_STATE_DAILY, alarmState)
            Const.typeAlarmRelease -> preferenceHelper.saveStringPreferences(Const.PREF_ALARM_STATE_RELEASE, alarmState)
        }
    }

    override fun loadAlarmState(type: Int): String? {
        return when(type){
            Const.typeAlarmDaily -> preferenceHelper.loadStringPreferences(Const.PREF_ALARM_STATE_DAILY)
            Const.typeAlarmRelease -> preferenceHelper.loadStringPreferences(Const.PREF_ALARM_STATE_RELEASE)
            else -> return null
        }
    }

    override fun deleteAlarmState(type: Int) {
        when (type){
            Const.typeAlarmDaily -> preferenceHelper.deleteStringPreferences(Const.PREF_ALARM_STATE_DAILY)
            Const.typeAlarmRelease -> preferenceHelper.deleteStringPreferences(Const.PREF_ALARM_STATE_RELEASE)
        }
    }

    override fun saveFirebaseMessagingToken(token: String) {
        preferenceHelper.saveStringPreferences(Const.PREF_FIREBASE_MESSAGING_TOKEN, token)
    }

    override fun getSearchData(urlType: String, query: String, callback: DataSource.GetDataCallback) {
        // do nothing
    }

    override fun getAllResult(callback: DataSource.GetResultRoomCallback, type: Int) {
        resultDao.getAllResult(type)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onShowProgressDialog() }
            .subscribe(object : SingleObserver<List<Result>>{
                override fun onSuccess(t: List<Result>) {
                    callback.onSuccess(t)
                }
                override fun onSubscribe(d: Disposable) {
                    // do nothing
                }
                override fun onError(e: Throwable) {
                    callback.onFailed(e.message)
                }
            })
    }

    override fun getResultById(id: Int, callback: DataSource.GetResultByIdRoomCallback) {
        resultDao.getResultById(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onShowProgressDialog() }
            .subscribe(object : SingleObserver<Result>{
                override fun onSuccess(t: Result) {
                    callback.onSuccess(t)
                }
                override fun onSubscribe(d: Disposable) {
                    // do nothing
                }
                override fun onError(e: Throwable) {
                    callback.onFailed(e.message)
                }
            })
    }

    override fun bulkInsertResult(data: Result, callback: DataSource.RoomCallback) {
        Completable.fromAction{resultDao.bulkInsert(data)}
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onShowProgressDialog() }
            .subscribe(object : CompletableObserver{
                override fun onComplete() {
                    callback.onSuccess()
                }
                override fun onSubscribe(d: Disposable) {
                    // do nothing
                }
                override fun onError(e: Throwable) {
                    callback.onFailed(e.message)
                }
            })
    }

    override fun deleteResult(data: Result, callback: DataSource.RoomCallback) {
        Completable.fromAction { resultDao.deleteData(data) }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onShowProgressDialog() }
            .subscribe(object : CompletableObserver{
                override fun onComplete() {
                    callback.onSuccess()
                }
                override fun onSubscribe(d: Disposable) {
                    // do nothing
                }
                override fun onError(e: Throwable) {
                    callback.onFailed(e.message)
                }
            })
    }

    override fun getData(page: Int, urlType: String, urlFilter: String, callback: DataSource.GetDataCallback) {
        // do nothing
    }

    override fun saveLanguage(language: String) {
        preferenceHelper.saveStringPreferences(Const.PREF_LANGUAGE, language)
    }

    override fun loadLanguage(): String? {
        return preferenceHelper.loadStringPreferences(Const.PREF_LANGUAGE)
    }

}