package com.anangkur.madesubmission1.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.anangkur.madesubmission1.R
import android.content.Intent
import android.os.Bundle

class StackRemoteViewsFactory(private val context: Context): RemoteViewsService.RemoteViewsFactory{

    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {
        // do nothing
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {
        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.example_appwidget_preview))
        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.example_appwidget_preview))
        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.example_appwidget_preview))
        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.example_appwidget_preview))
        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.example_appwidget_preview))
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(p0: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.item_widget)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[p0])

        val extras = Bundle()
        extras.putInt(FavouriteWidget().extraItem, p0)

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)

        return rv
    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {
        // do nothing
    }

}