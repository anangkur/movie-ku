package com.anangkur.madesubmission1.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anangkur.madesubmission1.utils.Const
import kotlinx.android.parcel.Parcelize

@Entity(tableName = Const.DATABASE_RESULT)
@Parcelize
data class Result(
    @PrimaryKey val id: Int,
    var type: Int,
    var favourite: Boolean,
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String?,
    val original_name: String?,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val name: String?,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int
): Parcelable