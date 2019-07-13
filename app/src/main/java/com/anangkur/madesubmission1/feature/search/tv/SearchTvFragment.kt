package com.anangkur.madesubmission1.feature.search.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.feature.search.SearchActivity
import com.anangkur.madesubmission1.feature.search.SearchViewModel

class SearchTvFragment : Fragment(){

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = (requireActivity() as SearchActivity).viewModel
    }
}