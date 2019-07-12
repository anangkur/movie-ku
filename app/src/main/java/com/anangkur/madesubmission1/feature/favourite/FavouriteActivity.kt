package com.anangkur.madesubmission1.feature.favourite

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.local.LocalDataSource
import com.anangkur.madesubmission1.data.local.SharedPreferenceHelper
import com.anangkur.madesubmission1.data.local.room.ResultDatabase
import com.anangkur.madesubmission1.data.remote.RemoteDataSource
import com.anangkur.madesubmission1.feature.custom.TabAdapter
import com.anangkur.madesubmission1.feature.favourite.movie.FavouriteMovieFragment
import com.anangkur.madesubmission1.feature.favourite.tv.FavouriteTvFragment
import com.anangkur.madesubmission1.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_favourite.*

class FavouriteActivity : AppCompatActivity() {

    private lateinit var tabAdapter: TabAdapter
    lateinit var viewModel: FavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        setupToolbar()
        setupTabAdapter()
        setupViewPager()
        setupCustomTab()
        setupSelectedCustomTab(0)
        setupViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("ACTIVITY_RESULT", "FavouriteActivity requestCode: $requestCode, resultCode: $resultCode")
        supportFragmentManager.findFragmentById(R.id.vp_fav)?.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }
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
        vp_fav.adapter = tabAdapter
        tab_fav.setupWithViewPager(vp_fav)
        vp_fav.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
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
        for (i in 0 until tab_fav.tabCount) {
            val tab = tab_fav.getTabAt(i)
            tab?.customView = null
            tab?.customView = tabAdapter.getTabView(i, this)
        }
    }

    private fun setupSelectedCustomTab(position: Int){
        val tab = tab_fav.getTabAt(position)
        tab?.customView = null
        tab?.customView = tabAdapter.getSelectedTabView(position, this)
    }

    fun startActivity(context: Context){
        context.startActivity(Intent(context, FavouriteActivity::class.java))
    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                application,
                Repository(
                    LocalDataSource(
                        SharedPreferenceHelper(this),
                        ResultDatabase.getInstance(this)?.getDao()!!
                    ),
                    RemoteDataSource
                )
            )
        ).get(FavouriteViewModel::class.java)

        viewModel.apply {

        }
    }
}
