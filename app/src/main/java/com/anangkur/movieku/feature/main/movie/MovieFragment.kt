package com.anangkur.movieku.feature.main.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.movieku.R
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.feature.detail.DetailActivity
import com.anangkur.movieku.feature.main.MainActivity
import com.anangkur.movieku.feature.main.MainItemListener
import com.anangkur.movieku.feature.main.MainViewModel
import com.anangkur.movieku.utils.Const
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
        if (savedInstanceState == null){
            movieViewModel.getHorizontalData(1)
            movieViewModel.getVerticalData(1)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Const.EXTRA_STATE_ROTATE, Const.STATE_ROTATE)
    }

    private fun setupViewModel(){
        movieViewModel = (requireActivity() as MainActivity).viewModel
        movieViewModel.apply {
            horizontalDataLive.observe(this@MovieFragment, Observer {
                layout_error_horizontal.visibility = View.GONE
                movieHorizontalAdapter.setRecyclerData(it)
            })
            verticalLiveData.observe(this@MovieFragment, Observer {
                layout_error_vertical.visibility = View.GONE
                movieVerticalAdapter.setRecyclerData(it)
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
        DetailActivity().startActivity(requireActivity(), data, Const.TYPE_MOVIE, Const.requestCodeFavMovie)
    }
}