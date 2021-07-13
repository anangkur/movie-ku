package com.anangkur.movieku.feature.main.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.databinding.FragmentMovieBinding
import com.anangkur.movieku.feature.detail.DetailActivity
import com.anangkur.movieku.feature.main.MainActivity
import com.anangkur.movieku.feature.main.MainItemListener
import com.anangkur.movieku.feature.main.MainViewModel
import com.anangkur.movieku.utils.Const

class MovieFragment : Fragment(), MainItemListener {

    private lateinit var movieViewModel: MainViewModel
    private lateinit var movieVerticalAdapter: MovieVerticalAdapter
    private lateinit var movieHorizontalAdapter: MovieHorizontalAdapter
    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentMovieBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
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
            horizontalDataLive.observe(viewLifecycleOwner) {
                binding.layoutErrorHorizontal.root.visibility = View.GONE
                movieHorizontalAdapter.setRecyclerData(it)
            }
            verticalLiveData.observe(viewLifecycleOwner) {
                binding.layoutErrorVertical.root.visibility = View.GONE
                movieVerticalAdapter.setRecyclerData(it)
            }
            showProgressHorizontalLive.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbHorizontal.visibility = View.VISIBLE
                    binding.layoutErrorHorizontal.root.visibility = View.GONE
                } else {
                    binding.pbHorizontal.visibility = View.GONE
                }
            }
            showProgressVerticalLive.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbVertical.visibility = View.VISIBLE
                    binding.layoutErrorVertical.root.visibility = View.GONE
                } else {
                    binding.pbVertical.visibility = View.GONE
                }
            }
            showErrorHorizontalLive.observe(viewLifecycleOwner) {
                binding.layoutErrorHorizontal.root.visibility = View.VISIBLE
                binding.layoutErrorHorizontal.root.setOnClickListener { getHorizontalData(1) }
            }
            showErrorVerticalLive.observe(viewLifecycleOwner) {
                binding.layoutErrorVertical.root.visibility = View.VISIBLE
                binding.layoutErrorVertical.root.setOnClickListener { getVerticalData(1) }
            }
        }
    }

    private fun setupVerticalAdapter(){
        movieVerticalAdapter = MovieVerticalAdapter(this)
        binding.recyclerVertical.apply {
            adapter = movieVerticalAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupHorizontalAdapter(){
        movieHorizontalAdapter = MovieHorizontalAdapter(this)
        binding.recyclerHorizontal.apply {
            adapter = movieHorizontalAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onClickItem(data: Result) {
        DetailActivity.startActivity(requireActivity(), data, Const.TYPE_MOVIE, Const.requestCodeFavMovie)
    }
}