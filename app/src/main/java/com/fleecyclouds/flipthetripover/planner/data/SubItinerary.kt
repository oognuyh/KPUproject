package com.fleecyclouds.flipthetripover.planner.data

data class SubItinerary (
    val date: String, // 해당일 e.g. "2020-04-03
    var contents: ArrayList<Content>?
)