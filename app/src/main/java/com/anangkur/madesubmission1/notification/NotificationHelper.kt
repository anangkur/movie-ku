package com.anangkur.madesubmission1.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.feature.main.MainActivity
import com.anangkur.madesubmission1.utils.Const

class NotificationHelper(private val context: Context){

    fun createNoticication(title: String, message: String, type: String, itemId: String) {
        showNotification(title, itemId, buildNotification(createIntent(), type, title, message))
    }

    private fun showNotification(title: String, itemId: String, mBuilder: NotificationCompat.Builder){
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannelId = Const.notificationChannelId

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(notificationChannelId, title, importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.WHITE
            notificationChannel.enableVibration(true)

            mBuilder.setChannelId(notificationChannelId)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }

        mNotificationManager.notify(itemId.length, mBuilder.build())
    }

    private fun createIntent(): PendingIntent {
        val resultIntent = Intent(context, MainActivity::class.java)

        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    private fun buildNotification(pendingIntent: PendingIntent, type: String, title: String, message: String): NotificationCompat.Builder{
        return NotificationCompat.Builder(context, type)
            .setSmallIcon(R.drawable.ic_movie_filter_white_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }
}