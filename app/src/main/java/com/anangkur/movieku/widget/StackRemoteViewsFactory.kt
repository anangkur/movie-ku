package com.anangkur.movieku.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.anangkur.movieku.R
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.os.Bundle
import com.anangkur.movieku.BuildConfig.baseImageUrl
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.utils.Const
import com.anangkur.movieku.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class StackRemoteViewsFactory(private val context: Context): RemoteViewsService.RemoteViewsFactory{

    private val mWidgetItems = ArrayList<Result>()
    private var cursor: Cursor? = null

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
        if (cursor != null){
            cursor!!.close()
        }

        val identityToken = Binder.clearCallingIdentity()

        cursor = context.contentResolver.query(Const.URI_MOVIE, null, null, null, null)
        val listResult = Utils.convertCursorIntoList(cursor!!)
        mWidgetItems.addAll(listResult)

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(p0: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.item_widget)

        val bitmap = Glide.with(context)
            .asBitmap()
            .load("$baseImageUrl${mWidgetItems[p0].backdrop_path?:mWidgetItems[p0].poster_path}")
            .apply(RequestOptions().centerCrop())
            .apply(RequestOptions().transform(RoundedCorners(48)))
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.imageView, bitmap)

        val extras = Bundle()
        extras.putString(Const.EXTRA_WIDGET, mWidgetItems[p0].original_name?:mWidgetItems[p0].original_title)

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