package com.anangkur.movieku.feature.notificationSetting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anangkur.movieku.data.DataSource
import com.anangkur.movieku.data.Repository
import com.anangkur.movieku.utils.Const

class NotifiationSettingViewModel(application: Application, private val repository: Repository): AndroidViewModel(application){

    val alarmStateDailyLive = MutableLiveData<String>()
    val alarmStateReleaseLive = MutableLiveData<String>()

    fun saveAlarmStateDaily(state: String){
        repository.saveAlarmState(state, Const.typeAlarmDaily)
    }

    fun loadAlarmStateDaily(){
        repository.loadAlarmState(Const.typeAlarmDaily, object: DataSource.PreferencesCallback{
            override fun onSuccess(data: String?) {
                alarmStateDailyLive.value = data
            }
        })
    }

    fun saveAlarmStateRelease(state: String){
        repository.saveAlarmState(state, Const.typeAlarmRelease)
    }

    fun loadAlarmStateRelease(){
        repository.loadAlarmState(Const.typeAlarmRelease, object: DataSource.PreferencesCallback{
            override fun onSuccess(data: String?) {
                alarmStateReleaseLive.value = data
            }
        })
    }
}