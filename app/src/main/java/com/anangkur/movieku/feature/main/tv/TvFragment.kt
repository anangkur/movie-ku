package com.anangkur.movieku.feature.main.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.databinding.FragmentTvBinding
import com.anangkur.movieku.feature.detail.DetailActivity
import com.anangkur.movieku.feature.main.MainActivity
import com.anangkur.movieku.feature.main.MainItemListener
import com.anangkur.movieku.feature.main.MainViewModel
import com.anangkur.movieku.utils.Const

class TvFragment : Fragment(), MainItemListener {

    private lateinit var tvViewModel: MainViewModel
    private lateinit var tvNewAdapter: TvAdapter
    private lateinit var tvPopularAdapter: TvAdapter
    private lateinit var tvRatingAdapter: TvAdapter
    private lateinit var binding: FragmentTvBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentTvBinding.inflate(inflater, container, false).also { binding = it }.root
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
            tvNewLive.observe(viewLifecycleOwner) {
                tvNewAdapter.setRecyclerData(it)
                binding.layoutErrorTvNew.root.visibility = View.GONE
            }
            tvPopularLive.observe(viewLifecycleOwner) {
                tvPopularAdapter.setRecyclerData(it)
                binding.layoutErrorTvPopular.root.visibility = View.GONE
            }
            tvRatingLive.observe(viewLifecycleOwner) {
                tvRatingAdapter.setRecyclerData(it)
                binding.layoutErrorTvRating.root.visibility = View.GONE
            }
            showProgressNew.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbTvNew.visibility = View.VISIBLE
                    binding.layoutErrorTvNew.root.visibility = View.GONE
                } else {
                    binding.pbTvNew.visibility = View.GONE
                }
            }
            showProgressPopular.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbTvPopular.visibility = View.VISIBLE
                    binding.layoutErrorTvPopular.root.visibility = View.GONE
                } else {
                    binding.pbTvPopular.visibility = View.GONE
                }
            }
            showProgressRating.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbTvRating.visibility = View.VISIBLE
                    binding.layoutErrorTvRating.root.visibility = View.GONE
                } else {
                    binding.pbTvRating.visibility = View.GONE
                }
            }
            showErrorNew.observe(viewLifecycleOwner) {
                binding.layoutErrorTvNew.root.visibility = View.VISIBLE
                binding.layoutErrorTvNew.root.setOnClickListener { getTvNew(1) }
            }
            showErrorPopular.observe(viewLifecycleOwner) {
                binding.layoutErrorTvPopular.root.visibility = View.VISIBLE
                binding.layoutErrorTvPopular.root.setOnClickListener { getTvPopular(1) }
            }
            showErrorRating.observe(viewLifecycleOwner) {
                binding.layoutErrorTvRating.root.visibility = View.VISIBLE
                binding.layoutErrorTvRating.root.setOnClickListener { getTvRating(1) }
            }
        }
    }

    private fun setupTvNewAdapter(){
        tvNewAdapter = TvAdapter(this)
        binding.recyclerTvNew.apply {
            adapter = tvNewAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setupTvPopularAdapter(){
        tvPopularAdapter = TvAdapter(this)
        binding.recyclerTvPopular.apply {
            adapter = tvPopularAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setupTvRatingAdapter(){
        tvRatingAdapter = TvAdapter(this)
        binding.recyclerTvRating.apply {
            adapter = tvRatingAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    override fun onClickItem(data: Result) {
        DetailActivity.startActivity(requireActivity(), data, Const.TYPE_TV, Const.requestCodeFavTv)
    }
}