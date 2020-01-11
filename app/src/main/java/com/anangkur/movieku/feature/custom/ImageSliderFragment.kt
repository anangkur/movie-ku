package com.anangkur.movieku.feature.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anangkur.movieku.BuildConfig.baseImageUrl
import com.anangkur.movieku.R
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.feature.detail.DetailActivity
import com.anangkur.movieku.utils.Const
import com.anangkur.movieku.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_image_slider.view.*

class ImageSliderFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.getParcelable<Result>(Const.BUNDLE_RESULT)
        data?.let { result ->
            Glide.with(requireContext())
                .load("$baseImageUrl${result.backdrop_path?:data.poster_path}")
                .apply(RequestOptions().centerCrop())
                .apply(RequestOptions().placeholder(Utils.createCircularProgressDrawable(requireContext())))
                .apply(RequestOptions().error(R.drawable.ic_broken_image))
                .into(view.iv_slider)
            view.tv_title.text = result.title
            view.tv_overview.text = result.overview
            view.setOnClickListener { DetailActivity().startActivity(requireActivity(), result, Const.TYPE_MOVIE, Const.requestCodeFavMovie) }
        }
    }

    companion object{
        fun getInstance(data: Result): ImageSliderFragment{
            val fragment = ImageSliderFragment()
            val bundle = Bundle()
            bundle.putParcelable(Const.BUNDLE_RESULT, data)
            fragment.arguments = bundle
            return fragment
        }
    }
}