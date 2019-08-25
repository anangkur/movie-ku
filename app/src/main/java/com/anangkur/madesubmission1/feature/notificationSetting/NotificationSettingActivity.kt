package com.anangkur.madesubmission1.feature.notificationSetting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.local.LocalDataSource
import com.anangkur.madesubmission1.data.local.SharedPreferenceHelper
import com.anangkur.madesubmission1.data.remote.RemoteDataSource
import com.anangkur.madesubmission1.notification.AlarmReceiver
import com.anangkur.madesubmission1.utils.Const
import com.anangkur.madesubmission1.data.ViewModelFactory
import kotlinx.android.synthetic.main.activity_notification_setting.*

class NotificationSettingActivity : AppCompatActivity() {

    lateinit var viewModel: NotifiationSettingViewModel

    private var isFirstOpenDaily = true
    private var isFirstOpenRelease = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_setting)

        setupToolbar()
        setupViewModel()
        setupSwitchDaily()
        setupSwitchRelease()
        viewModel.loadAlarmStateDaily()
        viewModel.loadAlarmStateRelease()
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        title = resources.getString(R.string.toolbar_notification_setting)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun setupViewModel(){
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(NotifiationSettingViewModel::class.java)

        viewModel.apply {
            alarmStateDailyLive.observe(this@NotificationSettingActivity, Observer {
                when(it){
                    Const.alarmStateActive -> switch_daily.isChecked = true
                    Const.alarmStateInActive -> switch_daily.isChecked = false
                    else -> switch_daily.isChecked = false
                }
            })
            alarmStateReleaseLive.observe(this@NotificationSettingActivity, Observer {
                when(it){
                    Const.alarmStateActive -> switch_release.isChecked = true
                    Const.alarmStateInActive -> switch_release.isChecked = false
                    else -> switch_release.isChecked = false
                }
            })
        }
    }

    private fun setupSwitchDaily(){
        switch_daily.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && isFirstOpenDaily && viewModel.alarmStateDailyLive.value == Const.alarmStateActive){
                isFirstOpenDaily = false
            }else{
                if (isChecked){
                    AlarmReceiver().setupAlarm(this, resources.getString(R.string.alarm_daily_title), resources.getString(R.string.alarm_daily_message), Const.typeAlarmDaily, Const.alarmDailyTime)
                    viewModel.saveAlarmStateDaily(Const.alarmStateActive)
                }else{
                    AlarmReceiver().cancelAlarm(this, Const.typeAlarmDaily)
                    viewModel.saveAlarmStateDaily(Const.alarmStateInActive)
                }
            }
        }
    }

    private fun setupSwitchRelease(){
        switch_release.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && isFirstOpenRelease && viewModel.alarmStateReleaseLive.value == Const.alarmStateActive){
                isFirstOpenRelease = false
            }else{
                if (isChecked){
                    AlarmReceiver().setupAlarm(this, resources.getString(R.string.alarm_release_title), resources.getString(R.string.alarm_release_message), Const.typeAlarmRelease, Const.alarmNewReleaseTime)
                    viewModel.saveAlarmStateRelease(Const.alarmStateActive)
                }else{
                    AlarmReceiver().cancelAlarm(this, Const.typeAlarmRelease)
                    viewModel.saveAlarmStateRelease(Const.alarmStateInActive)
                }
            }
        }
    }

    fun startActivity(context: Context){
        context.startActivity(Intent(context, NotificationSettingActivity::class.java))
    }
}
