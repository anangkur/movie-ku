package com.anangkur.madesubmission1.feature.search

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.Repository
import com.anangkur.madesubmission1.data.local.LocalDataSource
import com.anangkur.madesubmission1.data.local.SharedPreferenceHelper
import com.anangkur.madesubmission1.data.remote.RemoteDataSource
import com.anangkur.madesubmission1.feature.custom.TabAdapter
import com.anangkur.madesubmission1.feature.search.movie.SearchMovieFragment
import com.anangkur.madesubmission1.feature.search.tv.SearchTvFragment
import com.anangkur.madesubmission1.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*
import androidx.appcompat.widget.SearchView
import com.anangkur.madesubmission1.utils.Utils
import kotlinx.android.synthetic.main.activity_search.toolbar


class SearchActivity : AppCompatActivity() {

    lateinit var viewModel: SearchViewModel
    private lateinit var tabAdapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupToolbar()
        setupTabAdapter()
        setupViewPager()
        setupCustomTab()
        setupSelectedCustomTab(0)
        setupViewModel()
        setupSearchView()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.compositeDisposable.clear()
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        title = ""
    }

    private fun setupTabAdapter(){
        tabAdapter = TabAdapter(supportFragmentManager)
        val resourceMovieActive =  ContextCompat.getDrawable(this,R.drawable.ic_movie_active) as Drawable
        val resourceMovieInActive =  ContextCompat.getDrawable(this,R.drawable.ic_movie_inactive) as Drawable
        val resourceTvActive =  ContextCompat.getDrawable(this,R.drawable.ic_tv_active) as Drawable
        val resourceTvInActive =  ContextCompat.getDrawable(this,R.drawable.ic_tv_inactive) as Drawable
        tabAdapter.addFragment(SearchMovieFragment(), getString(R.string.tab_movie), resourceMovieActive, resourceMovieInActive)
        tabAdapter.addFragment(SearchTvFragment(), getString(R.string.tab_tv), resourceTvActive, resourceTvInActive)
    }

    private fun setupViewPager(){
        vp_search.adapter = tabAdapter
        tab_search.setupWithViewPager(vp_search)
        vp_search.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
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
        for (i in 0 until tab_search.tabCount) {
            val tab = tab_search.getTabAt(i)
            tab?.customView = null
            tab?.customView = tabAdapter.getTabView(i, this)
        }
    }

    private fun setupSelectedCustomTab(position: Int){
        val tab = tab_search.getTabAt(position)
        tab?.customView = null
        tab?.customView = tabAdapter.getSelectedTabView(position, this)
    }

    fun startActivity(context: Context){
        context.startActivity(Intent(context, SearchActivity::class.java))
    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                application,
                Repository(
                    LocalDataSource(
                        SharedPreferenceHelper(this),
                        this
                    ),
                    RemoteDataSource
                )
            )
        ).get(SearchViewModel::class.java)

        viewModel.apply {}
    }

    private fun setupSearchView(){
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getAllMovie(it)
                    viewModel.getAllTv(it)
                }
                Utils.hideSoftKeyboard(this@SearchActivity)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
