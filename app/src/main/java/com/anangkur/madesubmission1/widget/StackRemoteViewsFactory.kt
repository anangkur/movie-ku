package com.anangkur.madesubmission1.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.anangkur.madesubmission1.R
import android.content.Intent
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import com.anangkur.madesubmission1.BuildConfig.baseImageUrl
import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.MovieProvider
import com.anangkur.madesubmission1.data.local.room.ResultDatabase
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class StackRemoteViewsFactory(private val context: Context): RemoteViewsService.RemoteViewsFactory{

    private val mWidgetItems = ArrayList<Result>()

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
        class Task : AsyncTask<Void, Void, Cursor>(){
            override fun doInBackground(vararg p0: Void?): Cursor? {
                return context.contentResolver.query(MovieProvider().URI_MOVIE, null, null, null, null)
            }
            override fun onPostExecute(result: Cursor?) {
                result?.let {
                    val listResult = Utils.convertCursorIntoList(it)
                    mWidgetItems.addAll(listResult)
                }
            }
            override fun onPreExecute() {

            }
        }
        Task().execute()
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
            .apply(RequestOptions().placeholder(Utils.createCircularProgressDrawable(context)))
            .apply(RequestOptions().error(R.drawable.ic_broken_image))
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.imageView, bitmap)

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