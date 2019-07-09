package com.anangkur.madesubmission1.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.feature.detail.DetailViewModel
import com.anangkur.madesubmission1.feature.main.MainViewModel

class ViewModelFactory (private val application: Application, private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application, repository)
                    isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application, repository)
                    else -> throw IllegalArgumentException("Unknown ViewModel kelas: ${modelClass.name}")
                }
            } as T
}