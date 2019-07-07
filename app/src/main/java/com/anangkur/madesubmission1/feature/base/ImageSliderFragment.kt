package com.anangkur.madesubmission1.feature.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.feature.detail.DetailActivity
import com.anangkur.madesubmission1.feature.main.MainViewModel
import com.anangkur.madesubmission1.utils.Const
import com.anangkur.madesubmission1.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_image_slider.view.*

class ImageSliderFragment: Fragment(){

    private var data: Result? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data?.let { data ->
            Glide.with(requireContext())
                .load("${Const.baseImageUrl}${data.backdrop_path}")
                .apply(RequestOptions().centerCrop())
                .apply(RequestOptions().placeholder(Utils.createCircularProgressDrawable(requireContext())))
                .apply(RequestOptions().error(R.drawable.ic_broken_image))
                .into(view.iv_slider)
            view.tv_title.text = data.title
            view.tv_overview.text = data.overview
            view.setOnClickListener { DetailActivity().startActivity(requireContext(), data) }
        }
    }

    fun setData(data: Result){
        this.data = data
    }
}