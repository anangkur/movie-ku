package com.anangkur.madesubmission1.feature.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.utils.Const
import com.anangkur.madesubmission1.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailPresenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    override fun onStart() {
        super.onStart()
        setupToolbar()
        setupPresenter()
        detailPresenter.getDataFromIntent(intent)
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar_detail)
        supportActionBar?.title = ""
        toolbar_detail.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp)
        toolbar_detail.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupPresenter(){
        detailPresenter = DetailPresenter(object : DetailView{
            override fun onDataReady(data: Result) {
                setupDataToView(data)
            }
        })
    }

    fun startActivity(context: Context, data: Result){
        val intent = Intent(context, DetailActivity::class.java)
            .putExtra(Const.EXTRA_DETAIL, Gson().toJson(data))
        context.startActivity(intent)
    }

    private fun setupDataToView(data: Result){
        Glide.with(this)
            .load("${Const.baseImageUrl}${data.poster_path}")
            .apply(RequestOptions().centerCrop())
            .into(iv_movie)
        tv_title.text = data.original_title
        rating.rating = Utils.nomalizeRating(data.vote_average)
        tv_release_date.text = data.release_date
        tv_language.text = data.original_language
        tv_popularity.text = data.popularity.toString()
        tv_overview.text = data.overview
    }
}
