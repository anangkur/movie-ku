package com.anangkur.madesubmission1.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anangkur.madesubmission1.data.Injection
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.feature.detail.DetailViewModel
import com.anangkur.madesubmission1.feature.languageSetting.LanguageSettingViewModel
import com.anangkur.madesubmission1.feature.main.MainViewModel

class ViewModelFactory private constructor(private val application: Application, private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application, repository)
                    isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application, repository)
                    isAssignableFrom(LanguageSettingViewModel::class.java) -> LanguageSettingViewModel(application, repository)
                    else -> throw IllegalArgumentException("Unknown ViewModel kelas: ${modelClass.name}")
                }
            } as T

    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(mApplication: Application) =
                INSTANCE ?: synchronized(
                    ViewModelFactory::class.java) {
                    INSTANCE
                        ?: ViewModelFactory(
                            mApplication,
                            Injection.provideRepository(mApplication.applicationContext)
                        )
                            .also { INSTANCE = it }
                }
    }
}