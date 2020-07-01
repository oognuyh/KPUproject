package com.fleecyclouds.flipthetripover.utils

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RecommedAPIHelper {

    private val RECOMENDED_BASE_URL = "http://49.50.162.243:5000/"

    private val instance by lazy {
        /*val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()*/

        val retrofit = Retrofit.Builder().baseUrl(RECOMENDED_BASE_URL)
            //.client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RecommendAPI::class.java)
    }

    fun getRecommended(contentId: String): Call<Recommended> {
        return instance.requestRecommended(contentId)
    }
}