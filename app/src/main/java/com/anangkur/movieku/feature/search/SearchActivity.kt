package com.anangkur.movieku.feature.search

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.anangkur.movieku.R
import com.anangkur.movieku.feature.custom.TabAdapter
import com.anangkur.movieku.feature.search.movie.SearchMovieFragment
import com.anangkur.movieku.feature.search.tv.SearchTvFragment
import com.anangkur.movieku.data.ViewModelFactory
import androidx.appcompat.widget.SearchView
import com.anangkur.movieku.databinding.ActivitySearchBinding
import com.anangkur.movieku.utils.Utils

class SearchActivity : AppCompatActivity() {

    lateinit var viewModel: SearchViewModel
    private lateinit var tabAdapter: TabAdapter
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySearchBinding.inflate(layoutInflater).also { binding = it }.root)

        setupToolbar()
        setupTabAdapter()
        setupViewPager()
        setupCustomTab()
        setupSelectedCustomTab(0)
        setupViewModel()
        setupSearchView()
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
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
        binding.vpSearch.adapter = tabAdapter
        binding.tabSearch.setupWithViewPager(binding.vpSearch)
        binding.vpSearch.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
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
        for (i in 0 until binding.tabSearch.tabCount) {
            val tab = binding.tabSearch.getTabAt(i)
            tab?.customView = null
            tab?.customView = tabAdapter.getTabView(i, this)
        }
    }

    private fun setupSelectedCustomTab(position: Int){
        val tab = binding.tabSearch.getTabAt(position)
        tab?.customView = null
        tab?.customView = tabAdapter.getSelectedTabView(position, this)
    }

    fun startActivity(context: Context){
        context.startActivity(Intent(context, SearchActivity::class.java))
    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(SearchViewModel::class.java)
        viewModel.apply {}
    }

    private fun setupSearchView(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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
