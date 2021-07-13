package com.anangkur.movieku.feature.favourite

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.anangkur.movieku.R
import com.anangkur.movieku.feature.custom.TabAdapter
import com.anangkur.movieku.feature.favourite.movie.FavouriteMovieFragment
import com.anangkur.movieku.feature.favourite.tv.FavouriteTvFragment
import com.anangkur.movieku.data.ViewModelFactory
import com.anangkur.movieku.databinding.ActivityFavouriteBinding

class FavouriteActivity : AppCompatActivity() {

    private lateinit var tabAdapter: TabAdapter
    lateinit var viewModel: FavouriteViewModel
    private lateinit var binding: ActivityFavouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityFavouriteBinding.inflate(layoutInflater).also { binding = it }.root)

        setupToolbar()
        setupTabAdapter()
        setupViewPager()
        setupCustomTab()
        setupSelectedCustomTab(0)
        setupViewModel()
        viewModel.getAllData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.getAllData()
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        title = resources.getString(R.string.toolbar_favourite)
    }

    private fun setupTabAdapter(){
        tabAdapter = TabAdapter(supportFragmentManager)
        val resourceMovieActive =  ContextCompat.getDrawable(this,R.drawable.ic_movie_active) as Drawable
        val resourceMovieInActive =  ContextCompat.getDrawable(this,R.drawable.ic_movie_inactive) as Drawable
        val resourceTvActive =  ContextCompat.getDrawable(this,R.drawable.ic_tv_active) as Drawable
        val resourceTvInActive =  ContextCompat.getDrawable(this,R.drawable.ic_tv_inactive) as Drawable
        tabAdapter.addFragment(FavouriteMovieFragment(), getString(R.string.tab_movie), resourceMovieActive, resourceMovieInActive)
        tabAdapter.addFragment(FavouriteTvFragment(), getString(R.string.tab_tv), resourceTvActive, resourceTvInActive)
    }

    private fun setupViewPager(){
        binding.vpFav.adapter = tabAdapter
        binding.tabFav.setupWithViewPager(binding.vpFav)
        binding.vpFav.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                // do nothing
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // do nothing
            }
            override fun onPageSelected(position: Int) {
                setupCustomTab()
                setupSelectedCustomTab(position)
            }
        })
    }

    private fun setupCustomTab(){
        for (i in 0 until binding.tabFav.tabCount) {
            val tab = binding.tabFav.getTabAt(i)
            tab?.customView = null
            tab?.customView = tabAdapter.getTabView(i, this)
        }
    }

    private fun setupSelectedCustomTab(position: Int){
        val tab = binding.tabFav.getTabAt(position)
        tab?.customView = null
        tab?.customView = tabAdapter.getSelectedTabView(position, this)
    }

    fun startActivity(context: Context){
        context.startActivity(Intent(context, FavouriteActivity::class.java))
    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(FavouriteViewModel::class.java)

        viewModel.apply {}
    }
}
