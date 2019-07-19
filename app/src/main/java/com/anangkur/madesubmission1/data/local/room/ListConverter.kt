package com.anangkur.madesubmission1.data.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    @TypeConverter
    fun toList(json: String): List<Int>? {
        return Gson().fromJson(json, object : TypeToken<List<Int>>() {}.type)
    }

    @TypeConverter
    fun toString(list: List<Int>?): String {
        return Gson().toJson(list)
    }
}