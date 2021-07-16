package com.anangkur.movieku.feature.main.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.movieku.BuildConfig.baseImageUrl
import com.anangkur.movieku.R
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.databinding.ItemMainVerticalBinding
import com.anangkur.movieku.feature.main.MainItemListener
import com.anangkur.movieku.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class MovieVerticalAdapter(val itemListener: MainItemListener): RecyclerView.Adapter<MovieVerticalAdapter.ViewHolder>() {

    private val items = ArrayList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMainVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setRecyclerData(data: List<Result>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMainVerticalBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Result){
            val newRating = Utils.nomalizeRating(data.vote_average)
            Glide.with(binding.root.context)
                .load("$baseImageUrl${data.poster_path}")
                .apply(RequestOptions().centerCrop())
                .apply(RequestOptions().placeholder(Utils.createCircularProgressDrawable(itemView.context)))
                .apply(RequestOptions().error(R.drawable.ic_broken_image))
                .into(binding.ivItem)
            binding.tvTitle.text = data.original_title
            binding.rating.rating = newRating
            binding.tvDuration.text = data.release_date
            binding.tvPopularity.text = data.popularity.toString()
            itemView.setOnClickListener { itemListener.onClickItem(data) }
        }
    }
}