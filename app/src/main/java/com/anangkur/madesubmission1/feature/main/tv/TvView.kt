package com.anangkur.madesubmission1.feature.main.tv

import com.anangkur.madesubmission1.data.model.TvParent

interface TvView {
    fun onPopularDataReady(data: List<TvParent>)
}