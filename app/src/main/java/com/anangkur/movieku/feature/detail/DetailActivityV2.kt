package com.anangkur.movieku.feature.detail

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.loadAny
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.BlurTransformation
import com.anangkur.movieku.BuildConfig
import com.anangkur.movieku.R
import com.anangkur.movieku.data.ViewModelFactory
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.databinding.ActivityDetailV2Binding
import com.anangkur.movieku.feature.notificationSetting.NotificationSettingActivity
import com.anangkur.movieku.utils.Const
import com.anangkur.movieku.utils.Utils
import com.anangkur.movieku.utils.blur
import com.google.android.material.snackbar.Snackbar

class DetailActivityV2: AppCompatActivity() {

    companion object{
        fun startActivity(context: Activity, data: Result, type: Int, requestCode: Int){
            val intent = Intent(context, DetailActivityV2::class.java)
                .putExtra(Const.EXTRA_DETAIL, data)
                .putExtra(Const.EXTRA_TYPE, type)
            context.startActivityForResult(intent, requestCode)
        }
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailV2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityDetailV2Binding.inflate(layoutInflater).also { binding = it }.root)
    }

    override fun onStart() {
        super.onStart()
        setupToolbar()
        setupViewModel()
        detailViewModel.getDataFromIntent(
            intent.getParcelableExtra(Const.EXTRA_DETAIL),
            intent.getIntExtra(Const.EXTRA_TYPE, 1)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_change_language -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.menu_notification_setting -> NotificationSettingActivity().startActivity(this)
        }
        return true
    }

    override fun onBackPressed() {
        setResult(Const.resultCodeDetail)
        super.onBackPressed()
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupViewModel(){
        detailViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(DetailViewModel::class.java)
        detailViewModel.apply {
            resultLive.observe(this@DetailActivityV2) {
                result = it
                getDataById(result)
            }
            successGetData.observe(this@DetailActivityV2) {
                if (it != null) {
                    setupDataToView(it)
                } else {
                    setupDataToView(result)
                }
            }
            successInsertResult.observe(this@DetailActivityV2) {
                if (it) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "${result.title ?: result.name} ${resources.getString(R.string.message_success_insert)}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    getDataById(result)
                }
            }
            successDeleteResult.observe(this@DetailActivityV2) {
                if (it) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "${result.title ?: result.name} ${resources.getString(R.string.message_success_delete)}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    getDataById(result)
                }
            }
            errorMessageLive.observe(this@DetailActivityV2) {
                Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupDataToView(data: Result){
        binding.ivBackground.load("${BuildConfig.baseImageUrl}${data.poster_path}") {
            placeholder(Utils.createCircularProgressDrawable(this@DetailActivityV2))
            transformations(BlurTransformation(this@DetailActivityV2))
        }
        binding.ivDetail.load("${BuildConfig.baseImageUrl}${data.backdrop_path}") {
            placeholder(Utils.createCircularProgressDrawable(this@DetailActivityV2))
        }
        binding.ivBlur.load("${BuildConfig.baseImageW300Url}${data.poster_path}") {
            placeholder(Utils.createCircularProgressDrawable(this@DetailActivityV2))
            transformations(BlurTransformation(this@DetailActivityV2))
        }
        binding.tvTitle.text = data.original_title?:data.original_name
        binding.rating.rating = Utils.nomalizeRating(data.vote_average)
        binding.tvReleaseDate.text = data.release_date?:"-"
        binding.tvLanguage.text = data.original_language
        binding.tvPopularity.text = data.popularity.toString()
        binding.tvOverview.text = data.overview
        setupFavourite(data)
    }

    private fun setupFavourite(data: Result){
        if (data.favourite == Const.favouriteStateTrue){
            binding.fabFav.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            binding.fabFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favourite_red_24dp))
            binding.fabFav.setOnClickListener {
                detailViewModel.deleteData(data.copy(favourite = Const.favouriteStateFalse))
            }
        }else{
            binding.fabFav.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            binding.fabFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favourite_gray_24dp))
            binding.fabFav.setOnClickListener {
                detailViewModel.bulkInsertData(data.copy(favourite = Const.favouriteStateTrue))
            }
        }
    }
}