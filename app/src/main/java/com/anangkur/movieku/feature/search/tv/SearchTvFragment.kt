package com.anangkur.movieku.feature.search.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.databinding.FragmentFavouriteTvBinding
import com.anangkur.movieku.feature.detail.DetailActivity
import com.anangkur.movieku.feature.favourite.FavouriteAdapter
import com.anangkur.movieku.feature.main.MainItemListener
import com.anangkur.movieku.feature.search.SearchActivity
import com.anangkur.movieku.feature.search.SearchViewModel
import com.anangkur.movieku.utils.Const

class SearchTvFragment : Fragment(), MainItemListener{

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: FavouriteAdapter
    private lateinit var binding: FragmentFavouriteTvBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentFavouriteTvBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupViewModel()
    }

    private fun setupAdapter(){
        adapter = FavouriteAdapter(this)
        binding.recyclerFav.apply {
            adapter = this@SearchTvFragment.adapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setupViewModel() {
        viewModel = (requireActivity() as SearchActivity).viewModel
        viewModel.apply {
            showProgressGetTv.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbRecyclerFav.visibility = View.VISIBLE
                    binding.layoutErrorFav.root.visibility = View.GONE
                } else {
                    binding.pbRecyclerFav.visibility = View.GONE
                }
            }
            showErrorGetTv.observe(viewLifecycleOwner) {
                // do nothing
            }
            tvLive.observe(viewLifecycleOwner) {
                adapter.setRecyclerData(it)
                binding.layoutErrorFav.root.visibility = View.GONE
            }
        }
    }

    override fun onClickItem(data: Result) {
        DetailActivity.startActivity(requireActivity(), data, Const.TYPE_TV, Const.requestCodeFavTv)
    }
}