package com.anangkur.movieku.feature.favourite.tv

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
import com.anangkur.movieku.feature.favourite.FavouriteActivity
import com.anangkur.movieku.feature.favourite.FavouriteAdapter
import com.anangkur.movieku.feature.favourite.FavouriteViewModel
import com.anangkur.movieku.feature.main.MainItemListener
import com.anangkur.movieku.utils.Const

class FavouriteTvFragment : Fragment(), MainItemListener{

    private lateinit var viewModel: FavouriteViewModel
    private lateinit var favMovieAdapter: FavouriteAdapter
    private lateinit var binding: FragmentFavouriteTvBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentFavouriteTvBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAdapter()
    }

    private fun setupViewModel(){
        viewModel = (requireActivity() as FavouriteActivity).viewModel
        viewModel.apply {
            tvLive.observe(viewLifecycleOwner) {
                favMovieAdapter.setRecyclerData(it)
                binding.layoutErrorFav.root.visibility = View.GONE
            }
            showErrorGetTv.observe(viewLifecycleOwner) {
                binding.layoutErrorFav.root.visibility = View.VISIBLE
            }
            showProgressGetTv.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbRecyclerFav.visibility = View.VISIBLE
                    binding.layoutErrorFav.root.visibility = View.GONE
                } else {
                    binding.pbRecyclerFav.visibility = View.GONE
                }
            }
        }
    }

    private fun setupAdapter(){
        favMovieAdapter = FavouriteAdapter(this)
        binding.recyclerFav.apply {
            adapter = favMovieAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onClickItem(data: Result) {
        DetailActivity.startActivity(requireActivity(), data, Const.TYPE_TV, Const.requestCodeFavTv)
    }
}