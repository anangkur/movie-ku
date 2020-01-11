package com.anangkur.movieku.data

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anangkur.movieku.feature.detail.DetailViewModel
import com.anangkur.movieku.feature.favourite.FavouriteViewModel
import com.anangkur.movieku.feature.main.MainViewModel
import com.anangkur.movieku.feature.notificationSetting.NotifiationSettingViewModel
import com.anangkur.movieku.feature.search.SearchViewModel

class ViewModelFactory (private val application: Application, private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application, repository)
                    isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application, repository)
                    isAssignableFrom(FavouriteViewModel::class.java) -> FavouriteViewModel(application, repository)
                    isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(application, repository)
                    isAssignableFrom(NotifiationSettingViewModel::class.java) -> NotifiationSettingViewModel(application, repository)
                    else -> throw IllegalArgumentException("Unknown ViewModel kelas: ${modelClass.name}")
                }
            } as T

    companion object{
        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(application, Injection.provideRepository(application.applicationContext)).also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}