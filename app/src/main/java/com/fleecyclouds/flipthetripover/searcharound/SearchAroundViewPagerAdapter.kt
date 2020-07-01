package com.fleecyclouds.flipthetripover.searcharound

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.fleecyclouds.flipthetripover.searcharound.subpages.SearchAroundLeports
import com.fleecyclouds.flipthetripover.searcharound.subpages.SearchAroundShopping
import com.fleecyclouds.flipthetripover.searcharound.subpages.SearchAroundSights

class SearchAroundViewPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val COUNT = 3
    private val titles = listOf<String>("관광지", "레포츠", "쇼핑")

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment =
                SearchAroundSights()
            1 -> fragment =
                SearchAroundLeports()
            2 -> fragment =
                SearchAroundShopping()
        }

        return fragment!!

    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}