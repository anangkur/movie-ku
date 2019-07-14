package com.anangkur.madesubmission1.feature.custom

import com.anangkur.madesubmission1.widget.StackRemoteViewsFactory
import android.content.Intent
import android.widget.RemoteViewsService


class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext)
    }
}