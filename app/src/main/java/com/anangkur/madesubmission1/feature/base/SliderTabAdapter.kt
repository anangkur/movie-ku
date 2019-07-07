package com.anangkur.madesubmission1.feature.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.anangkur.madesubmission1.feature.main.MainViewModel

class SliderTabAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val listFragment = ArrayList<ImageSliderFragment>()

    override fun getCount(): Int = listFragment.size
    override fun getItem(position: Int): Fragment = listFragment[position]

    fun addFragment(fragment: ImageSliderFragment){
        listFragment.add(fragment)
    }
}