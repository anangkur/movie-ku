package com.anangkur.madesubmission1.feature.main.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.data.model.TvParent
import com.anangkur.madesubmission1.feature.detail.DetailActivity
import com.anangkur.madesubmission1.feature.main.movie.MovieItemListener
import kotlinx.android.synthetic.main.fragment_tv.*

class TvFragment : Fragment(), MovieItemListener{

    private lateinit var tvPresenter: TvPresenter
    private lateinit var tvParentAdapter: TvParentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupPresenter()
        tvPresenter.createDataPopular(listOf(getString(R.string.category_popular), getString(R.string.category_new), getString(R.string.category_rating)))
    }

    private fun setupPresenter(){
        tvPresenter = TvPresenter(object : TvView{
            override fun onPopularDataReady(data: List<TvParent>) {
                tvParentAdapter.setRecyclerData(data)
            }
        })
    }

    private fun setupAdapter(){
        tvParentAdapter = TvParentAdapter(this)
        recycler_parent_tv.apply {
            adapter = tvParentAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onClickItem(data: Result) {
        DetailActivity().startActivity(requireContext(), data)
    }
}