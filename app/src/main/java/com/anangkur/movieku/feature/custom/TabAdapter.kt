package com.anangkur.movieku.feature.custom

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import com.anangkur.movieku.R
import com.anangkur.movieku.databinding.TabCustomBinding

class TabAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val listFragment = ArrayList<Fragment>()
    private val listTitle = ArrayList<String>()
    private val listIconActive = ArrayList<Drawable>()
    private val listIconInactive = ArrayList<Drawable>()

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    fun addFragment(fragment: Fragment, title: String, iconActive: Drawable, iconInactive: Drawable){
        listFragment.add(fragment)
        listTitle.add(title)
        listIconActive.add(iconActive)
        listIconInactive.add(iconInactive)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }

    fun getTabView(position: Int, context: Context): View {
        val binding: TabCustomBinding = TabCustomBinding.inflate(LayoutInflater.from(context))
        binding.ivTab.setImageDrawable(listIconInactive[position])
        binding.tvTab.apply {
            text = (listTitle[position])
            setTextColor(context.resources.getColor(R.color.gray))
        }
        return binding.root
    }

    fun getSelectedTabView(position: Int, context: Context): View {
        val binding: TabCustomBinding = TabCustomBinding.inflate(LayoutInflater.from(context))
        binding.ivTab.setImageDrawable(listIconActive[position])
        binding.tvTab.apply {
            text = (listTitle[position])
            setTextColor(context.resources.getColor(R.color.black))
        }
        return binding.root
    }
}