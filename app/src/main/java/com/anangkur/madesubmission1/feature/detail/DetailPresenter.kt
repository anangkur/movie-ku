package com.anangkur.madesubmission1.feature.detail

import android.content.Intent
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.utils.Const
import com.google.gson.Gson

class DetailPresenter(private val view: DetailView){

    fun getDataFromIntent(intent: Intent){
        view.onDataReady(intent.getParcelableExtra(Const.EXTRA_DETAIL))
    }
}