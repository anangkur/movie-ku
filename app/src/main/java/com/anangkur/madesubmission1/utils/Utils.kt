package com.anangkur.madesubmission1.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.anangkur.madesubmission1.data.model.Result

object Utils{
    fun nomalizeRating(oldValue: Float): Float{
        return ((oldValue-0)/10-0)*((5-0)+0)
    }

    fun createCircularProgressDrawable(context: Context): CircularProgressDrawable{
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 4f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        return circularProgressDrawable
    }

    fun convertCursorIntoList(data: Cursor): ArrayList<Result>{
        val listResult = ArrayList<Result>()
        while (data.moveToNext()){
            val result = Result(
                id = data.getInt(data.getColumnIndexOrThrow(Const.COLUMN_ID)),
                name = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_NAME)),
                title = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_TITLE)),
                type = data.getInt(data.getColumnIndexOrThrow(Const.COLUMN_TYPE)),
                favourite = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_FAVOURITE)).toBoolean(),
                adult = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_ADULT)).toBoolean(),
                backdrop_path = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_BACKDROP_PATH)),
                genre_ids = null,
                original_language = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_ORIGINAL_LANGUAGE)),
                original_name = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_ORIGINAL_NAME)),
                original_title = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_TITLE)),
                overview = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_OVERVIEW)),
                popularity = data.getDouble(data.getColumnIndexOrThrow(Const.COLUMN_POPULARITY)),
                poster_path = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_POSTER_PATH)),
                release_date = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_RELEASE_DATE)),
                video = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_VIDEO)).toBoolean(),
                vote_average = data.getFloat(data.getColumnIndexOrThrow(Const.COLUMN_VOTE_AVERAGE)),
                vote_count = data.getInt(data.getColumnIndexOrThrow(Const.COLUMN_VOTE_COUNT))
            )
            listResult.add(result)
        }
        return listResult
    }

    fun convertCursorIntoResult(data: Cursor): Result?{
        return if (data.moveToFirst()){
            Result(
                id = data.getInt(data.getColumnIndexOrThrow(Const.COLUMN_ID)),
                name = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_NAME)),
                title = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_TITLE)),
                type = data.getInt(data.getColumnIndexOrThrow(Const.COLUMN_TYPE)),
                favourite = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_FAVOURITE)).toBoolean(),
                adult = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_ADULT)).toBoolean(),
                backdrop_path = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_BACKDROP_PATH)),
                genre_ids = null,
                original_language = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_ORIGINAL_LANGUAGE)),
                original_name = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_ORIGINAL_NAME)),
                original_title = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_TITLE)),
                overview = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_OVERVIEW)),
                popularity = data.getDouble(data.getColumnIndexOrThrow(Const.COLUMN_POPULARITY)),
                poster_path = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_POSTER_PATH)),
                release_date = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_RELEASE_DATE)),
                video = data.getString(data.getColumnIndexOrThrow(Const.COLUMN_VIDEO)).toBoolean(),
                vote_average = data.getFloat(data.getColumnIndexOrThrow(Const.COLUMN_VOTE_AVERAGE)),
                vote_count = data.getInt(data.getColumnIndexOrThrow(Const.COLUMN_VOTE_COUNT))
            )
        }else{
            null
        }
    }

    fun convertResultToContentValue(data: Result): ContentValues{
        val values = ContentValues()
        values.put(Const.COLUMN_ID, data.id)
        values.put(Const.COLUMN_NAME, data.name)
        values.put(Const.COLUMN_TITLE, data.title)
        values.put(Const.COLUMN_TYPE, data.type)
        values.put(Const.COLUMN_FAVOURITE, data.favourite)
        values.put(Const.COLUMN_ADULT, data.adult)
        values.put(Const.COLUMN_BACKDROP_PATH, data.backdrop_path)
        values.put(Const.COLUMN_ORIGINAL_LANGUAGE, data.original_language)
        values.put(Const.COLUMN_ORIGINAL_NAME, data.original_name)
        values.put(Const.COLUMN_ORIGINAL_TITLE, data.original_title)
        values.put(Const.COLUMN_OVERVIEW, data.overview)
        values.put(Const.COLUMN_POPULARITY, data.popularity)
        values.put(Const.COLUMN_POSTER_PATH, data.poster_path)
        values.put(Const.COLUMN_RELEASE_DATE, data.release_date)
        values.put(Const.COLUMN_VIDEO, data.video)
        values.put(Const.COLUMN_VOTE_AVERAGE, data.vote_average)
        values.put(Const.COLUMN_VOTE_COUNT, data.vote_count)
        return values
    }
}