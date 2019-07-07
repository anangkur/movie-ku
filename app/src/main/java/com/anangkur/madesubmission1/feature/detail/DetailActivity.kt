package com.anangkur.madesubmission1.feature.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.Injection
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.feature.languageSetting.LanguageSettingActivity
import com.anangkur.madesubmission1.utils.Const
import com.anangkur.madesubmission1.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    override fun onStart() {
        super.onStart()
        setupToolbar()
        setupViewModel()
        detailViewModel.getDataFromIntent(intent.getParcelableExtra(Const.EXTRA_DETAIL))
        detailViewModel.loadLanguageSetting()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_change_language -> LanguageSettingActivity().startActivity(this)
        }
        return true
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupViewModel(){
        detailViewModel = DetailViewModel(Injection.provideRepository(this))
        detailViewModel.apply {
            resultLive.observe(this@DetailActivity, Observer {
                setupDataToView(it)
            })
            languageLive.observe(this@DetailActivity, Observer {
                if (it != null){
                    setupLanguage(it)
                }else{
                    setupLanguage(getString(R.string.language_english))
                }
            })
        }
    }

    fun startActivity(context: Context, data: Result){
        val intent = Intent(context, DetailActivity::class.java)
            .putExtra(Const.EXTRA_DETAIL, data)
        context.startActivity(intent)
    }

    private fun setupDataToView(data: Result){
        Glide.with(this)
            .load("${Const.baseImageUrl}${data.backdrop_path}")
            .apply(RequestOptions().centerCrop())
            .apply(RequestOptions().placeholder(Utils.createCircularProgressDrawable(this)))
            .apply(RequestOptions().error(R.drawable.ic_broken_image))
            .into(iv_movie)
        tv_title.text = data.original_title?:data.original_name
        rating.rating = Utils.nomalizeRating(data.vote_average)
        tv_release_date.text = data.release_date?:"-"
        tv_language.text = data.original_language
        tv_popularity.text = data.popularity.toString()
        tv_overview.text = data.overview
    }

    private fun setupLanguage(language: String){
        when (language){
            getString(R.string.language_indonesian) -> Utils.setLocale(getString(R.string.language_indonesian), this)
            getString(R.string.language_english) -> Utils.setLocale(getString(R.string.language_english), this)
        }
    }
}
