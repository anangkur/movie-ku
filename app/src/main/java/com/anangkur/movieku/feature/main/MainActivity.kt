package com.anangkur.movieku.feature.main

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.anangkur.movieku.R
import com.anangkur.movieku.feature.custom.ImageSliderFragment
import com.anangkur.movieku.feature.custom.SliderTabAdapter
import com.anangkur.movieku.feature.custom.TabAdapter
import com.anangkur.movieku.feature.favourite.FavouriteActivity
import com.anangkur.movieku.data.ViewModelFactory
import com.anangkur.movieku.databinding.ActivityMainBinding
import com.anangkur.movieku.feature.main.movie.MovieFragment
import com.anangkur.movieku.feature.main.tv.TvFragment
import com.anangkur.movieku.feature.notificationSetting.NotificationSettingActivity
import com.anangkur.movieku.feature.search.SearchActivity
import com.anangkur.movieku.utils.Const
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity(), MainActionListener{

    private lateinit var tabAdapter: TabAdapter
    lateinit var viewModel: MainViewModel

    private lateinit var pagerAdapter: SliderTabAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)

        setupToolbar()

        setupViewModel()
        if (savedInstanceState == null){
            viewModel.getSliderData(1)
            viewModel.loadFirebaseMessagingToken()
        }

        setupTabAdapter()
        setupViewPager()
        setupCustomTab()
        setupSelectedCustomTab(0)
        setupViewPagerSlider()

        binding.searchBar.setOnClickListener { this.onClickSearch() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Const.EXTRA_STATE_ROTATE, Const.STATE_ROTATE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting_favourite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_change_language -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.menu_favourite -> FavouriteActivity().startActivity(this)
            R.id.menu_notification_setting -> NotificationSettingActivity().startActivity(this)
        }
        return true
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(MainViewModel::class.java)

        viewModel.apply {
            sliderDataLive.observe(this@MainActivity) {
                for (item in it) {
                    pagerAdapter.addFragment(ImageSliderFragment.getInstance(item))
                }
                setupSliderPage(pagerAdapter)
                binding.layoutErrorSlider.root.visibility = View.GONE
            }
            showErrorSliderLive.observe(this@MainActivity) {
                binding.layoutErrorSlider.root.visibility = View.VISIBLE
                binding.layoutErrorSlider.root.setOnClickListener { getSliderData(1) }
            }
            showProgressSliderLive.observe(this@MainActivity) {
                if (it) {
                    binding.pbSlider.visibility = View.VISIBLE
                    binding.layoutErrorSlider.root.visibility = View.GONE
                } else {
                    binding.pbSlider.visibility = View.GONE
                }
            }
            firebaseToken.observe(this@MainActivity) {
                if (it == null) {
                    generateFirebaseToken()
                }
            }
        }
    }

    private fun setupTabAdapter(){
        tabAdapter = TabAdapter(supportFragmentManager)
        val resourceMovieActive =  ContextCompat.getDrawable(this,R.drawable.ic_movie_active) as Drawable
        val resourceMovieInActive =  ContextCompat.getDrawable(this,R.drawable.ic_movie_inactive) as Drawable
        val resourceTvActive =  ContextCompat.getDrawable(this,R.drawable.ic_tv_active) as Drawable
        val resourceTvInActive =  ContextCompat.getDrawable(this,R.drawable.ic_tv_inactive) as Drawable
        tabAdapter.addFragment(MovieFragment(), getString(R.string.tab_movie), resourceMovieActive, resourceMovieInActive)
        tabAdapter.addFragment(TvFragment(), getString(R.string.tab_tv), resourceTvActive, resourceTvInActive)
    }

    private fun setupViewPager(){
        binding.vpMain.adapter = tabAdapter
        binding.vpMain.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
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
        binding.tabMain.setupWithViewPager(binding.vpMain)
    }

    private fun setupCustomTab(){
        for (i in 0 until binding.tabMain.tabCount) {
            val tab = binding.tabMain.getTabAt(i)
            tab?.customView = null
            tab?.customView = tabAdapter.getTabView(i, this)
        }
    }

    private fun setupSelectedCustomTab(position: Int){
        val tab = binding.tabMain.getTabAt(position)
        tab?.customView = null
        tab?.customView = tabAdapter.getSelectedTabView(position, this)
    }

    private fun setupViewPagerSlider(){
        pagerAdapter = SliderTabAdapter(supportFragmentManager)
    }

    private fun setupSliderPage(pagerAdapter: SliderTabAdapter){
        binding.vpSlider.adapter = pagerAdapter
        binding.tabSlider.setupWithViewPager(binding.vpSlider, true)
        disableClickTablayout(binding.tabSlider)
    }

    private fun disableClickTablayout(tabLayout: TabLayout){
        for (i in 0 until tabLayout.tabCount){
            (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i).isEnabled = false
        }
    }

    override fun onClickSearch() {
        SearchActivity().startActivity(this)
    }

    private fun generateFirebaseToken(){
        FirebaseInstanceId.getInstance()
            .instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result?.token
            token?.let {
                viewModel.saveFirebaseMessagingToken(token)
                Log.d("generateToken", token)
            }
        })
    }
}
