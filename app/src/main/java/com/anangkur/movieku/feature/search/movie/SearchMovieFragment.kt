package com.anangkur.movieku.feature.search.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.anangkur.movieku.R
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.feature.detail.DetailActivity
import com.anangkur.movieku.feature.favourite.FavouriteAdapter
import com.anangkur.movieku.feature.main.MainItemListener
import com.anangkur.movieku.feature.search.SearchActivity
import com.anangkur.movieku.feature.search.SearchViewModel
import com.anangkur.movieku.utils.Const
import kotlinx.android.synthetic.main.fragment_favourite_movie.*

class SearchMovieFragment : Fragment(), MainItemListener{

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: FavouriteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = (requireActivity() as SearchActivity).viewModel
        viewModel.apply {
            showProgressGetMovie.observe(this@SearchMovieFragment, Observer {
                if (it){
                    pb_recycler_fav.visibility = View.VISIBLE
                    layout_error_fav.visibility = View.GONE
                }else{
                    pb_recycler_fav.visibility = View.GONE
                }
            })
            showErrorGetMovie.observe(this@SearchMovieFragment, Observer {
                // do nothing
            })
            movieLive.observe(this@SearchMovieFragment, Observer {
                adapter.setRecyclerData(it)
                layout_error_fav.visibility = View.GONE
            })
        }
    }

    private fun setupAdapter(){
        adapter = FavouriteAdapter(this)
        recycler_fav.apply {
            adapter = this@SearchMovieFragment.adapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onClickItem(data: Result) {
        DetailActivity().startActivity(requireActivity(), data, Const.TYPE_MOVIE, Const.requestCodeFavTv)
    }
}