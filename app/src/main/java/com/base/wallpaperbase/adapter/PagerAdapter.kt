package com.base.wallpaperbase.adapter

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.base.baselibrary.fragment.BaseTitleFragment

class PagerAdapter(fm:FragmentManager,var list: ArrayList<BaseTitleFragment<ViewDataBinding>>)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int) = list[position]

    override fun getCount() = list.size

    override fun getPageTitle(position: Int) = list[position].getTitle()
}