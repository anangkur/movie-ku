package com.anangkur.madesubmission1.feature.main

import com.anangkur.madesubmission1.data.model.Result

interface MainView{
    fun onVerticalDataReady(data: List<Result>)
    fun onHorizontalDataReady(data: List<Result>)
}