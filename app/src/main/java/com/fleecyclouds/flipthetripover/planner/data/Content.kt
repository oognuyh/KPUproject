package com.fleecyclouds.flipthetripover.planner.data

import com.skt.Tmap.TMapPoint

data class Content(
    val name: String, // 장소 이름 e.g. "한라산"
    val address: String,
    val longitude: Double, // 장소 위치 e.g. TMapPoint(123.312312, 43.234234)
    val latitude: Double
)