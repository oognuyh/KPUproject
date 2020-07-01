package com.fleecyclouds.flipthetripover.planner.data

data class Itinerary(
    val title: String?, // 여정 제목 e.g. "신나는 제주 여행!!"
    val startDate: String, // 시작일 e.g. "2020-04-03"
    val endDate: String, // 종료일 e.g. "2020-04-07"
    val subItinerary: List<SubItinerary>?
)