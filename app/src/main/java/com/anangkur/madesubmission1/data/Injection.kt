package com.anangkur.madesubmission1.data

import android.content.Context
import com.anangkur.madesubmission1.data.local.LocalDataSource
import com.anangkur.madesubmission1.data.local.SharedPreferenceHelper
import com.anangkur.madesubmission1.data.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): Repository {
        return Repository.getInstance(LocalDataSource(SharedPreferenceHelper(context)), RemoteDataSource)
    }
}