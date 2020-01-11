package com.anangkur.movieku.data.local.room

import android.database.Cursor
import androidx.room.*
import com.anangkur.movieku.data.model.Result
import io.reactivex.Single

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(data: Result): Long

    @Query("SELECT * FROM DATABASE_RESULT")
    fun getAllResult(): Single<List<Result>>

    @Delete
    fun deleteData(data: Result)

    @Query("DELETE FROM DATABASE_RESULT WHERE COLUMN_ID == :id")
    fun deleteById(id: Long): Int

    @Query("SELECT * FROM DATABASE_RESULT WHERE COLUMN_ID == :id")
    fun getResultById(id: Int): Single<Result>

    @Query("SELECT * FROM DATABASE_RESULT")
    fun getAllResultProvider(): Cursor

    @Query("SELECT * FROM DATABASE_RESULT WHERE COLUMN_ID == :id")
    fun getResultByIdProvider(id: Long): Cursor
}