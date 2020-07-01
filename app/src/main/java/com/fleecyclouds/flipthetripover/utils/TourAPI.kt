package com.fleecyclouds.flipthetripover.utils

import com.fleecyclouds.flipthetripover.data.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TourAPI {

    /* 공통정보조회 */
    @GET("detailCommon")
    fun requestDetailCommon(@QueryMap(encoded = true) params: Map<String, String>): Call<DetailCommonVO>

    /* 위치기반 관광정보조회 */
    @GET("locationBasedList")
    fun requestLocationBasedList(@QueryMap(encoded = true) params: Map<String, String>): Call<LocationBasedVO>

    /* 지역기반 관광정보조회 */
    @GET("areaBasedList")
    fun requestAreaBasedList(@QueryMap(encoded = true) params: Map<String, String>): Call<AreaBasedVO>

    /* 행사정보조회 */
    @GET("searchFestival")
    fun requestSearchFestival(@QueryMap(encoded = true) params: Map<String, String>): Call<SearchFestivalVO>

    /* 반복정보조회 */
    @GET("detailInfo")
    fun requestDetailInfo(@QueryMap(encoded = true) params: Map<String, String>): Call<DetailInfoVO>

    /* 소개정보조회 */
    @GET("detailIntro")
    fun requestDetailIntro(@QueryMap(encoded = true) params: Map<String, String>): Call<DetailIntroVO>

}