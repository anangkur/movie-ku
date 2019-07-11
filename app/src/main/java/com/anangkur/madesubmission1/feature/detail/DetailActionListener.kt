package com.anangkur.madesubmission1.feature.detail

import com.anangkur.madesubmission1.data.model.Result

interface DetailActionListener {
    fun onAddFavourite(data: Result)
    fun onRemoveFavourite(data: Result)
}