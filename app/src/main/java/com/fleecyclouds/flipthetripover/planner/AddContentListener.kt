package com.fleecyclouds.flipthetripover.planner

import com.fleecyclouds.flipthetripover.planner.data.Content

interface AddContentListener {
    fun removeSearchFragment(date: String, content: Content)
}