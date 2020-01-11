package com.anangkur.movieku.notification

import android.util.Log
import com.anangkur.movieku.data.local.SharedPreferenceHelper
import com.anangkur.movieku.utils.Const
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService: FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        SharedPreferenceHelper(this).saveStringPreferences(Const.PREF_FIREBASE_MESSAGING_TOKEN, p0.toString())
        Log.d("OnNewToken", "token firebase: $p0")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("MessagingService", "From: " + remoteMessage.from!!)
        Log.d("MessagingService", "From: " + remoteMessage.data)

        val tempTitle = remoteMessage.data?.get("title") ?: ""
        val tempMessage = remoteMessage.data?.get("body") ?: ""
        val tempItemId = remoteMessage.data?.get("id") ?: ""
        val tempType = remoteMessage.data?.get("type") ?: ""
        Log.d("MessagingService", "tempTitle: $tempTitle, tempMessage: $tempMessage, tempItemId: $tempItemId, tempType: $tempType")

        NotificationHelper(this).createNoticication(tempTitle, tempMessage, tempItemId.toInt(), null, null)
    }
}