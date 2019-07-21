package com.anangkur.madesubmission1.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.app.AlarmManager
import android.app.PendingIntent
import android.util.Log
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
        Log.d("ALARM_SETUP", "timeArray: ${timeArray[0]}, ${timeArray[1]}, ${timeArray[2]}")

        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, timeArray[0].toInt())
        calendar.set(Calendar.MINUTE, timeArray[1].toInt())
        calendar.set(Calendar.SECOND, timeArray[2].toInt())

        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 7) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, notifId, intent, 0)

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, "Repeating alarm diaktifkan", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context, type: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, type, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show()
    }
}