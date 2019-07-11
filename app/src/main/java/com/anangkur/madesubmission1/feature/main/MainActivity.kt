package com.anangkur.madesubmission1.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.local.LocalDataSource
import com.anangkur.madesubmission1.data.local.SharedPreferenceHelper
import com.anangkur.madesubmission1.data.local.room.ResultDatabase
import com.anangkur.madesubmission1.data.remote.RemoteDataSource
import com.anangkur.madesubmission1.feature.custom.ImageSliderFragment
import com.anangkur.madesubmission1.feature.custom.SliderTabAdapter
import com.anangkur.madesubmission1.feature.custom.TabAdapter
import com.anangkur.madesubmission1.feature.favourite.FavouriteActivity
import com.anangkur.madesubmission1.utils.ViewModelFactory
import com.anangkur.madesubmission1.feature.main.movie.MovieFragment
import com.anangkur.madesubmission1.feature.main.tv.TvFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity(){

    private lateinit var tabAdapter: TabAdapter
    lateinit var viewModel: MainViewModel

    private lateinit var pagerAdapter: SliderTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()

        setupViewModel()
        viewModel.getSliderData(1)

        setupTabAdapter()
        setupViewPager()
        setupCustomTab()
        setupSelectedCustomTab(0)
        setupViewPagerSlider()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting_favourite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_change_language -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.menu_favourite -> FavouriteActivity().startActivity(this)
        }
        return true
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
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
        ).get(MainViewModel::class.java)

        viewModel.apply {
            sliderDataLive.observe(this@MainActivity, Observer {
                for (item in it.results){
                    pagerAdapter.addFragment(ImageSliderFragment.getInstance(item))
                }
                setupSliderPage(pagerAdapter)
                layout_error_slider.visibility = View.GONE
            })
            showErrorSliderLive.observe(this@MainActivity, Observer {
                layout_error_slider.visibility = View.VISIBLE
                layout_error_slider.setOnClickListener { getSliderData(1) }
            })
            showProgressSliderLive.observe(this@MainActivity, Observer {
                if (it){
                    pb_slider.visibility = View.VISIBLE
                    layout_error_slider.visibility = View.GONE
                }else{
                    pb_slider.visibility = View.GONE
                }
            })
        }
    }

    private fun setupTabAdapter(){
        tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment(MovieFragment(), getString(R.string.tab_movie), resources.getDrawable(R.drawable.ic_movie_active), resources.getDrawable(R.drawable.ic_movie_inactive))
        tabAdapter.addFragment(TvFragment(), getString(R.string.tab_tv), resources.getDrawable(R.drawable.ic_tv_active), resources.getDrawable(R.drawable.ic_tv_inactive))
    }

    private fun setupViewPager(){
        vp_main.adapter = tabAdapter
        tab_main.setupWithViewPager(vp_main)
        vp_main.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
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
        for (i in 0 until tab_main.tabCount) {
            val tab = tab_main.getTabAt(i)
            tab?.customView = null
            tab?.customView = tabAdapter.getTabView(i, this)
        }
    }

    private fun setupSelectedCustomTab(position: Int){
        val tab = tab_main.getTabAt(position)
        tab?.customView = null
        tab?.customView = tabAdapter.getSelectedTabView(position, this)
    }

    private fun setupViewPagerSlider(){
        pagerAdapter = SliderTabAdapter(supportFragmentManager)
    }

    private fun setupSliderPage(pagerAdapter: SliderTabAdapter){
        vp_slider.adapter = pagerAdapter
        tab_slider.setupWithViewPager(vp_slider, true)
        disableClickTablayout(tab_slider)
    }

    private fun disableClickTablayout(tabLayout: TabLayout){
        for (i in 0 until tabLayout.tabCount){
            (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i).isEnabled = false
        }
    }
}
