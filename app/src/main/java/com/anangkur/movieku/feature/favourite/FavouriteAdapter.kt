package com.anangkur.movieku.feature.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.movieku.BuildConfig.baseImageUrl
import com.anangkur.movieku.R
import com.anangkur.movieku.data.model.Result
import com.anangkur.movieku.databinding.ItemFavouriteBinding
import com.anangkur.movieku.feature.main.MainItemListener
import com.anangkur.movieku.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class FavouriteAdapter(private val mainItemListener: MainItemListener): RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    private val items = ArrayList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFavouriteBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemFavouriteBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Result){
            binding.tvTitle.text = data.original_name?:data.original_title
            Glide.with(itemView.context)
                .load("$baseImageUrl${data.backdrop_path?:data.poster_path}")
                .apply(RequestOptions().centerCrop())
                .apply(RequestOptions().placeholder(Utils.createCircularProgressDrawable(itemView.context)))
                .apply(RequestOptions().error(R.drawable.ic_broken_image))
                .into(binding.ivItem)
            binding.rating.rating = Utils.nomalizeRating(data.vote_average)
            binding.root.setOnClickListener { mainItemListener.onClickItem(data) }
        }
    }
}