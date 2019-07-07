package com.anangkur.madesubmission1.feature.main.tv

import androidx.lifecycle.MutableLiveData
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.model.Response
import com.anangkur.madesubmission1.data.model.TvParent
import com.google.gson.Gson

class TvViewModel(private val repository: Repository) {

    val listTvParentLive = MutableLiveData<List<TvParent>>()

    fun createDataTvParent(categories: List<String>, jsonTV: List<String>){
        val listTvParent = ArrayList<TvParent>()
        for (i in 0 until categories.size){
            val result = Gson().fromJson(jsonTV[i], Response::class.java).results
            listTvParent.add(TvParent(categories[i], result))
        }
        listTvParentLive.value = listTvParent
    }
}