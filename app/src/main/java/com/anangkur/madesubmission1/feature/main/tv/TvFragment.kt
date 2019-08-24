package com.anangkur.madesubmission1.feature.main.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.feature.detail.DetailActivity
import com.anangkur.madesubmission1.feature.main.MainActivity
import com.anangkur.madesubmission1.feature.main.MainItemListener
import com.anangkur.madesubmission1.feature.main.MainViewModel
import com.anangkur.madesubmission1.utils.Const
import kotlinx.android.synthetic.main.fragment_tv.*

class TvFragment : Fragment(), MainItemListener {

    private lateinit var tvViewModel: MainViewModel
    private lateinit var tvNewAdapter: TvAdapter
    private lateinit var tvPopularAdapter: TvAdapter
    private lateinit var tvRatingAdapter: TvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTvNewAdapter()
        setupTvPopularAdapter()
        setupTvRatingAdapter()
        setupViewModel()

        if (savedInstanceState == null){
            tvViewModel.getTvNew(1)
            tvViewModel.getTvPopular(1)
            tvViewModel.getTvRating(1)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Const.EXTRA_STATE_ROTATE, Const.STATE_ROTATE)
    }

    private fun setupViewModel(){
        tvViewModel = (requireActivity() as MainActivity).viewModel
        tvViewModel.apply {
            tvNewLive.observe(this@TvFragment, Observer {
                tvNewAdapter.setRecyclerData(it)
                layout_error_tv_new.visibility = View.GONE
            })
            tvPopularLive.observe(this@TvFragment, Observer {
                tvPopularAdapter.setRecyclerData(it)
                layout_error_tv_popular.visibility = View.GONE
            })
            tvRatingLive.observe(this@TvFragment, Observer {
                tvRatingAdapter.setRecyclerData(it)
                layout_error_tv_rating.visibility = View.GONE
            })
            showProgressNew.observe(this@TvFragment, Observer {
                if (it){
                    pb_tv_new.visibility = View.VISIBLE
                    layout_error_tv_new.visibility = View.GONE
                }else{
                    pb_tv_new.visibility = View.GONE
                }
            })
            showProgressPopular.observe(this@TvFragment, Observer {
                if (it){
                    pb_tv_popular.visibility = View.VISIBLE
                    layout_error_tv_popular.visibility = View.GONE
                }else{
                    pb_tv_popular.visibility = View.GONE
                }
            })
            showProgressRating.observe(this@TvFragment, Observer {
                if (it){
                    pb_tv_rating.visibility = View.VISIBLE
                    layout_error_tv_rating.visibility = View.GONE
                }else{
                    pb_tv_rating.visibility = View.GONE
                }
            })
            showErrorNew.observe(this@TvFragment, Observer {
                layout_error_tv_new.visibility = View.VISIBLE
                layout_error_tv_new.setOnClickListener { getTvNew(1) }
            })
            showErrorPopular.observe(this@TvFragment, Observer {
                layout_error_tv_popular.visibility = View.VISIBLE
                layout_error_tv_popular.setOnClickListener { getTvPopular(1) }
            })
            showErrorRating.observe(this@TvFragment, Observer {
                layout_error_tv_rating.visibility = View.VISIBLE
                layout_error_tv_rating.setOnClickListener { getTvRating(1) }
            })
        }
    }

    private fun setupTvNewAdapter(){
        tvNewAdapter = TvAdapter(this)
        recycler_tv_new.apply {
            adapter = tvNewAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setupTvPopularAdapter(){
        tvPopularAdapter = TvAdapter(this)
        recycler_tv_popular.apply {
            adapter = tvPopularAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setupTvRatingAdapter(){
        tvRatingAdapter = TvAdapter(this)
        recycler_tv_rating.apply {
            adapter = tvRatingAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    override fun onClickItem(data: Result) {
        DetailActivity().startActivity(requireActivity(), data, Const.TYPE_TV, Const.requestCodeFavTv)
    }
}