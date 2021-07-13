package com.anangkur.movieku.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anangkur.movieku.utils.Const
import kotlinx.parcelize.Parcelize

@Entity(tableName = Const.DATABASE_RESULT)
@Parcelize
data class Result(

    @PrimaryKey
    @ColumnInfo(name = Const.COLUMN_ID)
    var id: Int,

    @ColumnInfo(name = Const.COLUMN_TYPE)
    var type: Int,

    @ColumnInfo(name = Const.COLUMN_FAVOURITE)
    var favourite: String?,

    @ColumnInfo(name = Const.COLUMN_ADULT)
    var adult: Boolean,

    @ColumnInfo(name = Const.COLUMN_BACKDROP_PATH)
    var backdrop_path: String?,

    @ColumnInfo(name = Const.COLUMN_GENRE_IDS)
    var genre_ids: List<Int>?,

    @ColumnInfo(name = Const.COLUMN_ORIGINAL_LANGUAGE)
    var original_language: String,

    @ColumnInfo(name = Const.COLUMN_ORIGINAL_TITLE)
    var original_title: String?,

    @ColumnInfo(name = Const.COLUMN_ORIGINAL_NAME)
    var original_name: String?,

    @ColumnInfo(name = Const.COLUMN_OVERVIEW)
    var overview: String,

    @ColumnInfo(name = Const.COLUMN_POPULARITY)
    var popularity: Double,

    @ColumnInfo(name = Const.COLUMN_POSTER_PATH)
    var poster_path: String?,

    @ColumnInfo(name = Const.COLUMN_RELEASE_DATE)
    var release_date: String?,

    @ColumnInfo(name = Const.COLUMN_TITLE)
    var title: String?,

    @ColumnInfo(name = Const.COLUMN_NAME)
    var name: String?,

    @ColumnInfo(name = Const.COLUMN_VIDEO)
    var video: Boolean,

    @ColumnInfo(name = Const.COLUMN_VOTE_AVERAGE)
    var vote_average: Float,

    @ColumnInfo(name = Const.COLUMN_VOTE_COUNT)
    var vote_count: Int

): Parcelable