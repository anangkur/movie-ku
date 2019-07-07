package com.anangkur.madesubmission1.feature.main.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.Injection
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.feature.detail.DetailActivity
import com.anangkur.madesubmission1.utils.Const
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(), MovieItemListener{

    private lateinit var movieViewModel: MovieViewModel
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
        movieViewModel.createHorizontalData(Const.jsonNowPlayingMovies)
        movieViewModel.createVerticalData(Const.jsonPopularMovies)
    }

    private fun setupPresenter(){
        movieViewModel = MovieViewModel(Injection.provideRepository(requireContext()))
        movieViewModel.apply {
            horizontalDataLive.observe(this@MovieFragment, Observer {
                movieHorizontalAdapter.setRecyclerData(it)
            })
            verticalLiveData.observe(this@MovieFragment, Observer {
                movieVerticalAdapter.setRecyclerData(it)
            })
        }
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