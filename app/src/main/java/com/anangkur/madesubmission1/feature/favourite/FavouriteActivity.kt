package com.anangkur.madesubmission1.feature.favourite

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.viewpager.widget.ViewPager
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.feature.custom.TabAdapter
import com.anangkur.madesubmission1.feature.favourite.movie.FavouriteMovieFragment
import com.anangkur.madesubmission1.feature.favourite.tv.FavouriteTvFragment
import kotlinx.android.synthetic.main.activity_favourite.*

class FavouriteActivity : AppCompatActivity() {

    private lateinit var tabAdapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        setupToolbar()
        setupTabAdapter()
        setupViewPager()
        setupCustomTab()
        setupSelectedCustomTab(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        title = resources.getString(R.string.toolbar_favourite)
    }

    private fun setupTabAdapter(){
        tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment(FavouriteMovieFragment(), getString(R.string.tab_movie), resources.getDrawable(R.drawable.ic_movie_active), resources.getDrawable(R.drawable.ic_movie_inactive))
        tabAdapter.addFragment(FavouriteTvFragment(), getString(R.string.tab_tv), resources.getDrawable(R.drawable.ic_tv_active), resources.getDrawable(R.drawable.ic_tv_inactive))
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
}
