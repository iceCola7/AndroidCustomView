package com.cxz.behaviorsample.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author chenxz
 * @date 2020/9/11
 * @desc
 */
class MyFragmentPagerAdapter : FragmentPagerAdapter {

    private var fragmentList = mutableListOf<Fragment>()

    constructor(fm: FragmentManager, fragmentList: MutableList<Fragment>) :
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        this.fragmentList = fragmentList
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}