package com.anangkur.movieku.feature.detail

import com.anangkur.movieku.data.model.Result

interface DetailActionListener {
    fun onAddFavourite(data: Result)
    fun onRemoveFavourite(data: Result)
}