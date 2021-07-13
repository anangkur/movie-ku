package com.anangkur.movieku.feature.detail

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.anangkur.movieku.BuildConfig.baseImageUrl
import com.anangkur.movieku.R
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.feature.notificationSetting.NotificationSettingActivity
import com.anangkur.movieku.utils.Const
import com.anangkur.movieku.utils.Utils
import com.anangkur.movieku.data.ViewModelFactory
import com.anangkur.movieku.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar

class DetailActivity: AppCompatActivity(), DetailActionListener {

    companion object{
        fun startActivity(context: Activity, data: Result, type: Int, requestCode: Int){
            val intent = Intent(context, DetailActivity::class.java)
                .putExtra(Const.EXTRA_DETAIL, data)
                .putExtra(Const.EXTRA_TYPE, type)
            context.startActivityForResult(intent, requestCode)
        }
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityDetailBinding.inflate(layoutInflater).also { binding = it }.root)
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
            resultLive.observe(this@DetailActivity) {
                result = it
                getDataById(result)
            }
            successGetData.observe(this@DetailActivity) {
                if (it != null) {
                    setupDataToView(it)
                } else {
                    setupDataToView(result)
                }
            }
            successInsertResult.observe(this@DetailActivity) {
                if (it) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "${result.title ?: result.name} ${resources.getString(R.string.message_success_insert)}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    getDataById(result)
                }
            }
            successDeleteResult.observe(this@DetailActivity) {
                if (it) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "${result.title ?: result.name} ${resources.getString(R.string.message_success_delete)}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    getDataById(result)
                }
            }
            errorMessageLive.observe(this@DetailActivity) {
                Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupDataToView(data: Result){
        Glide.with(this)
            .load("$baseImageUrl${data.backdrop_path?:data.poster_path}")
            .apply(RequestOptions().centerCrop())
            .apply(RequestOptions().placeholder(Utils.createCircularProgressDrawable(this)))
            .apply(RequestOptions().error(R.drawable.ic_broken_image))
            .into(binding.ivMovie)
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
            binding.fabFav.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
            binding.fabFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favourite_red_24dp))
            binding.fabFav.setOnClickListener {this.onRemoveFavourite(data)}
        }else{
            binding.fabFav.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
            binding.fabFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favourite_gray_24dp))
            binding.fabFav.setOnClickListener {this.onAddFavourite(data)}
        }
    }

    override fun onAddFavourite(data: Result) {
        detailViewModel.bulkInsertData(data.copy(favourite = Const.favouriteStateTrue))
    }

    override fun onRemoveFavourite(data: Result) {
        detailViewModel.deleteData(data.copy(favourite = Const.favouriteStateFalse))
    }
}