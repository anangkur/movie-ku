package com.anangkur.madesubmission1.feature.search.tv

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
import com.anangkur.madesubmission1.feature.main.MainItemListener
import com.anangkur.madesubmission1.feature.main.tv.TvAdapter
import com.anangkur.madesubmission1.feature.search.SearchActivity
import com.anangkur.madesubmission1.feature.search.SearchViewModel
import com.anangkur.madesubmission1.utils.Const
import kotlinx.android.synthetic.main.fragment_favourite_tv.*

class SearchTvFragment : Fragment(), MainItemListener{

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: TvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupViewModel()
    }

    private fun setupAdapter(){
        adapter = TvAdapter(this)
        recycler_fav.apply {
            adapter = this@SearchTvFragment.adapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setupViewModel() {
        viewModel = (requireActivity() as SearchActivity).viewModel
        viewModel.apply {
            showProgressGetTv.observe(this@SearchTvFragment, Observer {
                if (it){
                    pb_recycler_fav.visibility = View.VISIBLE
                    layout_error_fav.visibility = View.GONE
                }else{
                    pb_recycler_fav.visibility = View.GONE
                }
            })
            showErrorGetTv.observe(this@SearchTvFragment, Observer {
                // do nothing
            })
            tvLive.observe(this@SearchTvFragment, Observer {
                adapter.setRecyclerData(it)
                layout_error_fav.visibility = View.GONE
            })
        }
    }

    override fun onClickItem(data: Result) {
        DetailActivity().startActivity(requireActivity(), data, Const.TYPE_TV, Const.requestCodeFavTv)
    }
}