package com.anangkur.movieku.feature.notificationSetting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.anangkur.movieku.R
import com.anangkur.movieku.notification.AlarmReceiver
import com.anangkur.movieku.utils.Const
import com.anangkur.movieku.data.ViewModelFactory
import com.anangkur.movieku.databinding.ActivityNotificationSettingBinding

class NotificationSettingActivity : AppCompatActivity() {

    lateinit var viewModel: NotifiationSettingViewModel

    private var isFirstOpenDaily = true
    private var isFirstOpenRelease = true

    private lateinit var binding: ActivityNotificationSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityNotificationSettingBinding.inflate(layoutInflater).also { binding = it }.root)

        setupToolbar()
        setupViewModel()
        setupSwitchDaily()
        setupSwitchRelease()
        viewModel.loadAlarmStateDaily()
        viewModel.loadAlarmStateRelease()
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
        title = resources.getString(R.string.toolbar_notification_setting)
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun setupViewModel(){
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(NotifiationSettingViewModel::class.java)

        viewModel.apply {
            alarmStateDailyLive.observe(this@NotificationSettingActivity) {
                when (it) {
                    Const.alarmStateActive -> binding.switchDaily.isChecked = true
                    Const.alarmStateInActive -> binding.switchDaily.isChecked = false
                    else -> binding.switchDaily.isChecked = false
                }
            }
            alarmStateReleaseLive.observe(this@NotificationSettingActivity) {
                when (it) {
                    Const.alarmStateActive -> binding.switchRelease.isChecked = true
                    Const.alarmStateInActive -> binding.switchRelease.isChecked = false
                    else -> binding.switchRelease.isChecked = false
                }
            }
        }
    }

    private fun setupSwitchDaily(){
        binding.switchDaily.setOnCheckedChangeListener { buttonView, isChecked ->
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
        binding.switchRelease.setOnCheckedChangeListener { buttonView, isChecked ->
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
