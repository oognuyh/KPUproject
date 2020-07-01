package com.fleecyclouds.flipthetripover.planner.subpages

interface ItineraryListener {
    fun showSearchFragment(date: String)
    fun showMarkers(date: String)
}