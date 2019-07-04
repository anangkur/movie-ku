package com.anangkur.madesubmission1.feature.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anangkur.madesubmission1.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_image_slider.view.*

class ImageSliderFragment(private val imageUrl: String, private val title: String, private val overview: String): Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext())
            .load(imageUrl)
            .apply(RequestOptions().centerCrop())
            .into(view.iv_slider)
        view.tv_title.text = title
        view.tv_overview.text = overview
    }
}