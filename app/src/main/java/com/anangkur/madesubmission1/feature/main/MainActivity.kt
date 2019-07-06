package com.anangkur.madesubmission1.feature.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.feature.base.ImageSliderFragment
import com.anangkur.madesubmission1.feature.base.SliderTabAdapter
import com.anangkur.madesubmission1.feature.base.TabAdapter
import com.anangkur.madesubmission1.feature.main.languageSetting.LanguageSettingActivity
import com.anangkur.madesubmission1.feature.main.movie.MovieFragment
import com.anangkur.madesubmission1.feature.main.tv.TvFragment
import com.anangkur.madesubmission1.utils.Const
import com.anangkur.madesubmission1.utils.Utils
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity(){

    private lateinit var tabAdapter: TabAdapter
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupPresenter()
        setupTabAdapter()
        setupViewPager()
        setupCustomTab()
        setupSelectedCustomTab(0)
        setupViewPagerSlider()
        presenter.loadLanguageSetting(this)
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
    }

    private fun setupLanguage(language: String){
        when (language){
            getString(R.string.language_indonesian) -> Utils.setLocale(getString(R.string.language_indonesian), this)
            getString(R.string.language_english) -> Utils.setLocale(getString(R.string.language_english), this)
        }
    }

    private fun setupPresenter(){
        presenter = MainPresenter(object : MainView{
            override fun onLanguageReady(language: String?) {
                if (language != null){
                    setupLanguage(language)
                }else{
                    setupLanguage(getString(R.string.language_english))
                }
            }
        })
    }

    private fun setupTabAdapter(){
        tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment(MovieFragment(), resources.getString(R.string.tab_movie), resources.getDrawable(R.drawable.ic_movie_active), resources.getDrawable(R.drawable.ic_movie_inactive))
        tabAdapter.addFragment(TvFragment(), resources.getString(R.string.tab_tv), resources.getDrawable(R.drawable.ic_tv_active), resources.getDrawable(R.drawable.ic_tv_inactive))
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
        val pagerAdapter = SliderTabAdapter(supportFragmentManager)
        pagerAdapter.addFragment(ImageSliderFragment("${Const.baseImageUrl}${Const.spidermanPosterPath}", Const.spidermanTitle, Const.spidermanOverview))
        pagerAdapter.addFragment(ImageSliderFragment("${Const.baseImageUrl}${Const.shaftBackdropPath}", Const.shaftTitle, Const.shaftOverview))
        pagerAdapter.addFragment(ImageSliderFragment("${Const.baseImageUrl}${Const.toyStory4BackdropPath}", Const.toyStory4Title, Const.toyStory4Overview))
        pagerAdapter.addFragment(ImageSliderFragment("${Const.baseImageUrl}${Const.darkPhoenixBackdropPath}", Const.darkPhoenixTitle, Const.darkPhoenixOverview))
        pagerAdapter.addFragment(ImageSliderFragment("${Const.baseImageUrl}${Const.annabelleBackdropPath}", Const.annabelleTitle, Const.annabelleOverview))
        setupSliderPage(pagerAdapter)
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

    fun startActivityClearBackToHome(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        context.startActivity(intent)
    }
}
