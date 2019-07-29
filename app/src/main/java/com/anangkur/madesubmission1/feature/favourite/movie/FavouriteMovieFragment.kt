package com.anangkur.madesubmission1.feature.favourite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.feature.detail.DetailActivity
import com.anangkur.madesubmission1.feature.favourite.FavouriteActivity
import com.anangkur.madesubmission1.feature.favourite.FavouriteViewModel
import com.anangkur.madesubmission1.feature.main.MainItemListener
import com.anangkur.madesubmission1.feature.main.tv.TvAdapter
import com.anangkur.madesubmission1.utils.Const
import kotlinx.android.synthetic.main.fragment_favourite_movie.*

class FavouriteMovieFragment : Fragment(), MainItemListener{

    private lateinit var viewModel: FavouriteViewModel
    private lateinit var favMovieAdapter: TvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAdapter()
    }

    private fun setupViewModel(){
        viewModel = (requireActivity() as FavouriteActivity).viewModel
        viewModel.apply {
            movieLive.observe(this@FavouriteMovieFragment, Observer {
                favMovieAdapter.setRecyclerData(it)
                layout_error_fav.visibility = View.GONE
            })
            showErrorGetMovie.observe(this@FavouriteMovieFragment, Observer {
                layout_error_fav.visibility = View.VISIBLE
            })
            showProgressGetMovie.observe(this@FavouriteMovieFragment, Observer {
                if (it){
                    pb_recycler_fav.visibility = View.VISIBLE
                    layout_error_fav.visibility = View.GONE
                }else{
                    pb_recycler_fav.visibility = View.GONE
                }
            })
        }
    }

    private fun setupAdapter(){
        favMovieAdapter = TvAdapter(this)
        recycler_fav.apply {
            adapter = favMovieAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onClickItem(data: Result) {
        DetailActivity().startActivity(requireActivity(), data, Const.TYPE_MOVIE, Const.requestCodeFavMovie)
    }
}