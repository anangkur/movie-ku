package com.anangkur.madesubmission1.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.app.AlarmManager
import android.app.PendingIntent
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.utils.Const
import java.util.*

class AlarmReceiver : BroadcastReceiver(){

    override fun onReceive(p0: Context, p1: Intent) {
        val tempTitle = p1.getStringExtra(Const.EXTRA_ALARM_TITLE)
        val tempMessage = p1.getStringExtra(Const.EXTRA_ALARM_MESSAGE)
        val tempItemId = p1.getIntExtra(Const.EXTRA_NOTIF_ID, 0)
        NotificationHelper(p0).createNoticication(tempTitle, tempMessage, tempItemId)
    }

    fun setupAlarm(context: Context, title: String, message: String, notifId: Int, time: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(Const.EXTRA_ALARM_TITLE, title)
        intent.putExtra(Const.EXTRA_ALARM_MESSAGE, message)
        intent.putExtra(Const.EXTRA_NOTIF_ID, notifId)

        val timeArray = time.split(":")

        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, timeArray[0].toInt())
        calendar.set(Calendar.MINUTE, timeArray[1].toInt())
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, notifId, intent, 0)

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, context.resources.getString(R.string.message_alarm_activated), Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context, type: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, type, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, context.resources.getString(R.string.message_alarm_deactivated), Toast.LENGTH_SHORT).show()
    }
}