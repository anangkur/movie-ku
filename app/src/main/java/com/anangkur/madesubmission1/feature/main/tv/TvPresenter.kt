package com.anangkur.madesubmission1.feature.main.tv

import com.anangkur.madesubmission1.data.model.Response
import com.anangkur.madesubmission1.data.model.TvParent
import com.google.gson.Gson

class TvPresenter(private val view: TvView) {
    fun createDataPopular(categories: List<String>, jsonTV: List<String>){
        val listTvParent = ArrayList<TvParent>()
        for (i in 0 until categories.size){
            val result = Gson().fromJson(jsonTV[i], Response::class.java).results
            listTvParent.add(TvParent(categories[i], result))
        }
        view.onPopularDataReady(listTvParent)
    }
}