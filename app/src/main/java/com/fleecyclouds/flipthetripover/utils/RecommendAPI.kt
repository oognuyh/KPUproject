package com.fleecyclouds.flipthetripover.utils

import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.*

interface RecommendAPI {

    @POST("recommend")
    fun requestRecommended(@Query("contentId") contentId: String): Call<Recommended>
}

data class Recommended(
    val recommended: List<String>
)
