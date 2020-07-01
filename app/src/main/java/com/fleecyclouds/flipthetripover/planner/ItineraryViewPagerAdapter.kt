package com.fleecyclouds.flipthetripover.planner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.fleecyclouds.flipthetripover.planner.data.Content
import com.fleecyclouds.flipthetripover.planner.subpages.ItineraryFragment

class ItineraryViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var fragments = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    fun saveAll() {
        fragments.forEach {
            (it as ItineraryFragment).saveSubItinerary()
        }
    }

    fun saveContent(date: String, content: Content) {
        fragments.forEach {
            if ((it as ItineraryFragment).date == date) {
                // it.adapter.addContent(content)
                it.addContent(content)
            }
        }
    }
}