package com.anangkur.madesubmission1.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.feature.detail.DetailActivity
import com.anangkur.madesubmission1.feature.main.MainActivity
import com.anangkur.madesubmission1.utils.Const

class NotificationHelper(private val context: Context){

    fun createNoticication(title: String, message: String, itemId: Int, bitmap: Bitmap?, data: Result?) {
        if (itemId == Const.typeAlarmDaily){
            showNotification(title, itemId, buildNotification(createIntent(), title, message))
        }else{
            showNotification(title, itemId, buildNotificationWithImage(createIntentRelease(data), title, message, bitmap))
        }
    }

    private fun showNotification(title: String, itemId: Int, mBuilder: NotificationCompat.Builder){
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

        mNotificationManager.notify(itemId, mBuilder.build())
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

    private fun createIntentRelease(data: Result?): PendingIntent {
        val resultIntent = Intent(context, DetailActivity::class.java)

        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        resultIntent.putExtra(Const.EXTRA_DETAIL, data)
        resultIntent.putExtra(Const.EXTRA_TYPE, Const.TYPE_MOVIE)

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    private fun buildNotification(pendingIntent: PendingIntent, title: String, message: String): NotificationCompat.Builder{
        return NotificationCompat.Builder(context, Const.notificationChannelId)
            .setSmallIcon(R.drawable.ic_movie_filter_white_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    private fun buildNotificationWithImage(pendingIntent: PendingIntent, title: String, message: String, image: Bitmap?): NotificationCompat.Builder{
        return NotificationCompat.Builder(context, Const.notificationChannelId)
            .setSmallIcon(R.drawable.ic_movie_filter_white_24dp)
            .setLargeIcon(image)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigLargeIcon(null)
                .bigPicture(image))
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }
}