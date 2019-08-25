package com.anangkur.madesubmission1.feature.main

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anangkur.madesubmission1.data.DataSource
import com.anangkur.madesubmission1.data.Repository
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import com.anangkur.madesubmission1.BuildConfig.*
import com.anangkur.madesubmission1.argumentCaptor
import com.anangkur.madesubmission1.data.local.LocalDataSource
import com.anangkur.madesubmission1.data.local.SharedPreferenceHelper
import com.anangkur.madesubmission1.data.remote.RemoteDataSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify

class MainViewModelTest {

    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var repository: Repository
    private lateinit var application: Application
    private lateinit var context: Context
    private lateinit var viewModel: MainViewModel

    @Before
    fun setupViewModel(){
        application = mock()
        context = mock()
        repository = Repository(LocalDataSource(SharedPreferenceHelper(context), context), RemoteDataSource)
        viewModel = MainViewModel(application, repository)
    }

    @Test
    fun getTvPopular() {
        with(viewModel){
            getTvPopular(1)
            argumentCaptor<DataSource.GetDataCallback>().apply {
                verify(repository).getData(1, tvUrl, popularUrl, capture())
            }
        }
    }

    @Test
    fun getTvNew() {
    }

    @Test
    fun getTvRating() {
    }

    @Test
    fun getHorizontalData() {
    }

    @Test
    fun getVerticalData() {
    }

    @Test
    fun getSliderData() {
    }

    @Test
    fun saveFirebaseMessagingToken() {
    }

    @Test
    fun loadFirebaseMessagingToken() {
    }
}