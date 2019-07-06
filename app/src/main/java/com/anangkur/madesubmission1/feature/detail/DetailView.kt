package com.anangkur.madesubmission1.feature.detail

import com.anangkur.madesubmission1.data.model.Result

interface DetailView {
    fun onDataReady(data: Result)
    fun onLanguageReady(language: String?)
}