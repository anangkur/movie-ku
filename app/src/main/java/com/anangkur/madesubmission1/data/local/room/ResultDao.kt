package com.anangkur.madesubmission1.data.local.room

import androidx.room.*
import com.anangkur.madesubmission1.data.model.Result
import io.reactivex.Single

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(data: Result)

    @Query("SELECT * FROM DATABASE_RESULT WHERE type == :type")
    fun getAllResult(type: Int): Single<List<Result>>

    @Delete
    fun deleteData(data: Result)

    @Query("SELECT * FROM DATABASE_RESULT WHERE id == :id")
    fun getResultById(id: Int): Single<Result>
}