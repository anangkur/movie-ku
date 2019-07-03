package com.anangkur.madesubmission1.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.feature.detail.DetailActivity
import com.anangkur.madesubmission1.utils.Const
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainItemListener {

    private lateinit var verticalAdapter: MainVerticalAdapter
    private lateinit var horizontalAdapter: MainHorizontalAdapter

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        setupToolbar()
        setupHorizontalAdapter()
        setupVerticalAdapter()
        setupPresenter()
        mainPresenter.createHorizontalData(Const.jsonNewMovies)
        mainPresenter.createVerticalData(Const.jsonNowPlayingMovies)
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar_main)
        title_toolbar.text = resources.getString(R.string.toolbar_main)
        supportActionBar?.title = ""
    }

    private fun setupPresenter(){
        mainPresenter = MainPresenter(object: MainView{
            override fun onVerticalDataReady(data: List<Result>) {
                verticalAdapter.setRecyclerData(data)
            }
            override fun onHorizontalDataReady(data: List<Result>) {
                horizontalAdapter.setRecyclerData(data)
            }
        })
    }

    private fun setupVerticalAdapter(){
        verticalAdapter = MainVerticalAdapter(this)
        recycler_vertical.apply {
            adapter = verticalAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupHorizontalAdapter(){
        horizontalAdapter = MainHorizontalAdapter(this)
        recycler_horizontal.apply {
            adapter = horizontalAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onClickItem(data: Result) {
        DetailActivity().startActivity(this, data)
    }
}
