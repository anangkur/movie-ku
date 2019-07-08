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
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.feature.detail.DetailActivity
import com.anangkur.madesubmission1.feature.main.MainActivity
import com.anangkur.madesubmission1.feature.main.MainItemListener
import com.anangkur.madesubmission1.feature.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(), MainItemListener {

    private lateinit var movieViewModel: MainViewModel
    private lateinit var movieVerticalAdapter: MovieVerticalAdapter
    private lateinit var movieHorizontalAdapter: MovieHorizontalAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupHorizontalAdapter()
        setupVerticalAdapter()
        setupViewModel()
        movieViewModel.getHorizontalData(1)
        movieViewModel.getVerticalData(1)
    }

    private fun setupViewModel(){
        movieViewModel = (requireActivity() as MainActivity).viewModel
        movieViewModel.apply {
            horizontalDataLive.observe(this@MovieFragment, Observer {
                layout_error_horizontal.visibility = View.GONE
                movieHorizontalAdapter.setRecyclerData(it.results)
            })
            verticalLiveData.observe(this@MovieFragment, Observer {
                layout_error_vertical.visibility = View.GONE
                movieVerticalAdapter.setRecyclerData(it.results)
            })
            showProgressHorizontalLive.observe(this@MovieFragment, Observer {
                if (it){
                    pb_horizontal.visibility = View.VISIBLE
                    layout_error_horizontal.visibility = View.GONE
                }else{
                    pb_horizontal.visibility = View.GONE
                }
            })
            showProgressVerticalLive.observe(this@MovieFragment, Observer {
                if (it){
                    pb_vertical.visibility = View.VISIBLE
                    layout_error_vertical.visibility = View.GONE
                }else{
                    pb_vertical.visibility = View.GONE
                }
            })
            showErrorHorizontalLive.observe(this@MovieFragment, Observer {
                layout_error_horizontal.visibility = View.VISIBLE
                layout_error_horizontal.setOnClickListener { getHorizontalData(1) }
            })
            showErrorVerticalLive.observe(this@MovieFragment, Observer {
                layout_error_vertical.visibility = View.VISIBLE
                layout_error_vertical.setOnClickListener { getVerticalData(1) }
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