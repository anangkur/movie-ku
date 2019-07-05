package com.anangkur.madesubmission1.feature.main.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.model.Result
import com.anangkur.madesubmission1.data.model.TvParent
import com.anangkur.madesubmission1.feature.main.movie.MovieItemListener
import kotlinx.android.synthetic.main.item_tv_parent.view.*

class TvParentAdapter(private val itemListener: MovieItemListener): RecyclerView.Adapter<TvParentAdapter.ViewHolder>(){

    private val items = ArrayList<TvParent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tv_parent, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setRecyclerData(data: List<TvParent>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(data: TvParent){
            itemView.tv_title.text = data.title
            val adapter = TvAdapter(itemListener)
            itemView.recycler_tv.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                itemAnimator = DefaultItemAnimator()
            }
            adapter.setRecyclerData(data.data)
        }
    }
}