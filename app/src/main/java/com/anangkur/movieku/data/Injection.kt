package com.anangkur.movieku.data

import android.content.Context
import com.anangkur.movieku.data.local.LocalDataSource
import com.anangkur.movieku.data.local.SharedPreferenceHelper
import com.anangkur.movieku.data.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context) = Repository(LocalDataSource(SharedPreferenceHelper(context), context), RemoteDataSource)
}