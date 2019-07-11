package com.anangkur.madesubmission1.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.utils.Const

@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class ResultDatabase : RoomDatabase(){
    abstract fun getDao(): ResultDao

    companion object{
        private var INSTANCE: ResultDatabase? = null

        fun getInstance(context: Context): ResultDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, ResultDatabase::class.java, Const.DATABASE_RESULT).build()
            }
            return INSTANCE
        }
    }
}