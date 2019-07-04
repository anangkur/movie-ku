package com.anangkur.madesubmission1.feature.base

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import com.anangkur.madesubmission1.R
import kotlinx.android.synthetic.main.tab_custom.view.*
import androidx.core.content.ContextCompat

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
        val view = LayoutInflater.from(context).inflate(R.layout.tab_custom, null)
        view.iv_tab.setImageDrawable(listIconInactive[position])
        return view
    }

    fun getSelectedTabView(position: Int, context: Context): View {
        val view = LayoutInflater.from(context).inflate(R.layout.tab_custom, null)
        view.iv_tab.setImageDrawable(listIconActive[position])
        return view
    }
}