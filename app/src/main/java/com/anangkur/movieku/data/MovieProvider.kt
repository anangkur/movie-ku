package com.anangkur.movieku.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.anangkur.movieku.data.local.room.ResultDao
import com.anangkur.movieku.data.local.room.ResultDatabase
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.utils.Const
import com.anangkur.movieku.utils.Const.AUTHORITY
import com.anangkur.movieku.utils.Const.CODE_MOVIE_DIR
import com.anangkur.movieku.utils.Const.CODE_MOVIE_ITEM

class MovieProvider : ContentProvider(){

    private lateinit var resultDatabase: ResultDatabase
    private lateinit var resultDao: ResultDao

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, Const.DATABASE_RESULT, CODE_MOVIE_DIR)
        addURI(AUTHORITY, Const.DATABASE_RESULT + "/*", CODE_MOVIE_ITEM)
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        Log.d("MOVIE_PROVIDER", "insert")
        when (uriMatcher.match(p0)) {
            CODE_MOVIE_DIR -> {
                Log.d("MOVIE_PROVIDER", "insert movie dir")
                val context = context ?: return null
                val result = fromContentValues(p1!!)
                val id = resultDao.bulkInsert(result)
                context.contentResolver.notifyChange(p0, null)
                return ContentUris.withAppendedId(p0, id)
            }
            CODE_MOVIE_ITEM -> {
                Log.d("MOVIE_PROVIDER", "insert movie item $p0")
                throw IllegalArgumentException("Invalid URI, cannot insert with ID: $p0")
            }
            else -> throw IllegalArgumentException("Unknown URI: $p0")
        }
    }

    override fun query(p0: Uri, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?): Cursor? {
        val code = uriMatcher.match(p0)
        Log.d("MOVIE_PROVIDER", "query")
        if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
            Log.d("MOVIE_PROVIDER", "query enter")
            val context = context ?: return null
            val cursor = if (code == CODE_MOVIE_DIR) {
                Log.d("MOVIE_PROVIDER", "query movie dir")
                resultDao.getAllResultProvider()
            } else {
                Log.d("MOVIE_PROVIDER", "query movie item")
                resultDao.getResultByIdProvider(ContentUris.parseId(p0))
            }
            cursor.setNotificationUri(context.contentResolver, p0)
            return cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $p0")
        }
    }

    override fun onCreate(): Boolean {
        resultDatabase = ResultDatabase.getInstance(context!!)!!
        resultDao = resultDatabase.getDao()
        return true
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        Log.d("MOVIE_PROVIDER", "update")
        when (uriMatcher.match(p0)) {
            CODE_MOVIE_DIR -> {
                Log.d("MOVIE_PROVIDER", "update movie dir")
                throw IllegalArgumentException("Invalid URI, cannot update without ID$p0")
            }
            CODE_MOVIE_ITEM -> {
                Log.d("MOVIE_PROVIDER", "update movie item")
                val context = context ?: return 0
                val result = fromContentValues(p1!!)
                result.id = ContentUris.parseId(p0).toInt()
                val count = resultDao.bulkInsert(result)
                context.contentResolver.notifyChange(p0, null)
                return count.toInt()
            }
            else -> throw IllegalArgumentException("Unknown URI: $p0")
        }
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        Log.d("MOVIE_PROVIDER", "delete")
        when (uriMatcher.match(p0)) {
            CODE_MOVIE_DIR -> {
                Log.d("MOVIE_PROVIDER", "delete movie dir")
                throw IllegalArgumentException("Invalid URI, cannot update without ID $p0")
            }
            CODE_MOVIE_ITEM -> {
                Log.d("MOVIE_PROVIDER", "delete movie item")
                val context = context ?: return 0
                val count = resultDao.deleteById(ContentUris.parseId(p0))
                context.contentResolver.notifyChange(p0, null)
                return count
            }
            else -> throw IllegalArgumentException("Unknown URI: $p0")
        }
    }

    override fun getType(p0: Uri): String? {
        return when (uriMatcher.match(p0)) {
            CODE_MOVIE_DIR -> "vnd.android.cursor.dir/" + AUTHORITY + "." + Const.DATABASE_RESULT
            CODE_MOVIE_ITEM -> "vnd.android.cursor.item/" + AUTHORITY + "." + Const.DATABASE_RESULT
            else -> throw IllegalArgumentException("Unknown URI: $p0")
        }
    }

    private fun fromContentValues(values: ContentValues): Result {
        return Result(
            id = values.getAsInteger(Const.COLUMN_ID),
            name = values.getAsString(Const.COLUMN_NAME),
            title = values.getAsString(Const.COLUMN_TITLE),
            type = values.getAsInteger(Const.COLUMN_TYPE),
            favourite = values.getAsString(Const.COLUMN_FAVOURITE),
            adult = values.getAsBoolean(Const.COLUMN_ADULT),
            backdrop_path = values.getAsString(Const.COLUMN_BACKDROP_PATH),
            genre_ids = null,
            original_language = values.getAsString(Const.COLUMN_ORIGINAL_LANGUAGE),
            original_name = values.getAsString(Const.COLUMN_ORIGINAL_NAME),
            original_title = values.getAsString(Const.COLUMN_TITLE),
            overview = values.getAsString(Const.COLUMN_OVERVIEW),
            popularity = values.getAsDouble(Const.COLUMN_POPULARITY),
            poster_path = values.getAsString(Const.COLUMN_POSTER_PATH),
            release_date = values.getAsString(Const.COLUMN_RELEASE_DATE),
            video = values.getAsBoolean(Const.COLUMN_VIDEO),
            vote_average = values.getAsFloat(Const.COLUMN_VOTE_AVERAGE),
            vote_count = values.getAsInteger(Const.COLUMN_VOTE_COUNT)
        )
    }
}