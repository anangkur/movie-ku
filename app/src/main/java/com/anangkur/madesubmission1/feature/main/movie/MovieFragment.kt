package com.anangkur.madesubmission1.feature.main.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.feature.detail.DetailActivity
import com.anangkur.madesubmission1.utils.Const
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(), MovieItemListener{

    private lateinit var moviePresenter: MoviePresenter
    private lateinit var movieVerticalAdapter: MovieVerticalAdapter
    private lateinit var movieHorizontalAdapter: MovieHorizontalAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupHorizontalAdapter()
        setupVerticalAdapter()
        setupPresenter()
        moviePresenter.createHorizontalData(Const.jsonNewMovies)
        moviePresenter.createVerticalData(Const.jsonNowPlayingMovies)
    }

    private fun setupPresenter(){
        moviePresenter = MoviePresenter(object: MovieView{
            override fun onVerticalDataReady(data: List<Result>) {
                movieVerticalAdapter.setRecyclerData(data)
            }
            override fun onHorizontalDataReady(data: List<Result>) {
                movieHorizontalAdapter.setRecyclerData(data)
            }
        })
    }

    private fun setupVerticalAdapter(){
        movieVerticalAdapter = MovieVerticalAdapter(this)
        recycler_vertical.apply {
            adapter = movieVerticalAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupHorizontalAdapter(){
        movieHorizontalAdapter = MovieHorizontalAdapter(this)
        recycler_horizontal.apply {
            adapter = movieHorizontalAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onClickItem(data: Result) {
        DetailActivity().startActivity(requireContext(), data)
    }
}