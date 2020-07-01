package com.fleecyclouds.flipthetripover.utils

import android.util.Log
import com.fleecyclouds.flipthetripover.data.*
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TourAPIHelper {
    private val TAG = "TourAPIHelper"

    private val TOUR_API_BASE_URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/"
    private val TOUR_API_SERVICE_KEY = "NsIyBBw9ClLFRy0dl7G2RFkPd72rJS61GihChQZmtjCWXJadE%2Ff22gK%2Fy4zule0xN9YKkoC1cdY8IR1dwfK5Tw%3D%3D"

    private var numOfRows = "900"

    private val gson by lazy {
        GsonBuilder().registerTypeAdapter(DetailInfoBox::class.java, DetailInfoDeserializer()).create()
    }

    private val instance by lazy {
        val retrofit = Retrofit.Builder().baseUrl(TOUR_API_BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()

        retrofit.create(TourAPI::class.java)
    }

    /* 기본 파라미터 반환 */
    fun getDefaultParams(): MutableMap<String, String> {
        var params = mutableMapOf<String, String>()

        params.put("ServiceKey", TOUR_API_SERVICE_KEY)
        params.put("numOfRows", numOfRows)
        params.put("MobileOS", "ETC")
        params.put("MobileApp", "AppTest")
        params.put("_type", "json")

        return params
    }

    /* 한 페이지 결과 수 설정 */
    fun setNumOfRows(numOfRows: Int): TourAPIHelper {
        /* MIN/MAX 1 ~ 999 */
        if (numOfRows in 1..999) {
            this.numOfRows = numOfRows.toString()
        }
        else {
            Log.d(TAG, "Failed to set numOfRows")
        }

        return TourAPIHelper
    }

    /* 공통정보조회로 관광정보에 매핑되는 서브이미지목록을 조회 가능 */
    fun getDetailCommon(contentId: String, contentTypeId: String? = null,
                        defaultYN: String = "Y", firstImageYN: String = "Y", areacodeYN: String = "Y", catcodeYN: String = "Y",
                        addrinfoYN: String = "Y", mapinfoYN: String = "Y", overviewYN: String = "Y"): Call<DetailCommonVO> {
        var params = getDefaultParams()

        params.put("contentId", contentId)
        params.put("defaultYN", defaultYN)
        params.put("firstImageYN", firstImageYN)
        params.put("areacodeYN", areacodeYN)
        params.put("catcodeYN", catcodeYN)
        params.put("addrinfoYN", addrinfoYN)
        params.put("mapinfoYN", mapinfoYN)
        params.put("overviewYN", overviewYN)
        if (contentTypeId != null)
            params.put("contentTypeId", contentTypeId)

        return instance.requestDetailCommon(params)
    }

    /* 위치기반 관광정보조회로 위치기반 관광정보파라미터 타입에 따라서 제목순, 수정일순, 등록일순, 인기순 정렬검색목록을 조회 가능 */
    fun getLocationBasedList(pageNo: Int, arrange: String? = null, contentTypeId: String? = null,
                             mapX: Double, mapY: Double, radius: Int = 5000, listYN: String? = null): Call<LocationBasedVO> {
        var params =  getDefaultParams()

        params.put("pageNo", pageNo.toString())
        params.put("mapX", mapX.toString())
        params.put("mapY", mapY.toString())
        params.put("radius", radius.toString())
        if (arrange != null)
            params.put("arrange", arrange)
        if (contentTypeId != null)
            params.put("contentTypeId", contentTypeId)
        if (listYN != null)
            params.put("listYN", listYN)

        return instance.requestLocationBasedList(params)
    }

    /* 지역기반 관광정보조회로 지역기반 관광정보파라미터 타입에 따라서 제목순,수정일순,등록일순,인기순 정렬검색목록을 조회 가능 */
    fun getAreaBasedList(pageNo: Int, arrange: String? = null, cat1: String? = null, contentTypeId: String? = null,
                         areaCode: String? = null, sigunguCode: String? = null, cat2: String? = null, cat3: String? = null, listYN: String? = null): Call<AreaBasedVO> {
        var params: MutableMap<String, String> =  getDefaultParams()

        params.put("pageNo", pageNo.toString())
        if (arrange != null)
            params["arrange"] = arrange
        if (cat1 != null)
            params["cat1"] = cat1
        if (contentTypeId != null)
            params["contentTypeId"] = contentTypeId
        if (areaCode != null)
            params["areaCode"] = areaCode
        if (sigunguCode != null)
            params["sigunguCode"] = sigunguCode
        if (cat2 != null)
            params["cat2"] = cat2
        if (cat3 != null)
            params["cat3"] = cat3
        if (listYN != null)
            params["listYN"] = listYN

        return instance.requestAreaBasedList(params)
    }

    /* ARRANGE
       A=제목순, B=조회순, C=수정순, D=생성일순)
       대표이미지가 반드시 있는 정렬 (O=제목순, P=조회순, Q=수정일순, R=생성일순 */
    /* 행사정보조회로 행사정보목록을 조회하며. 컨텐츠 타입이 ‘행사’일 경우에만 유효 */
    fun getSearchFestival(pageNo: Int, arrange: String? = null, listYN: String? = null, areaCode: String? = null,
                          sigunguCode: String? = null, eventStartDate: String, eventEndDate: String? = null): Call<SearchFestivalVO> {
        var params =  getDefaultParams()

        params.put("pageNo", pageNo.toString())
        params.put("eventStartDate", eventStartDate)
        if (arrange != null)
            params.put("arrange", arrange)
        if (listYN != null)
            params.put("listYN", listYN)
        if (areaCode != null)
            params.put("areaCode", areaCode)
        if (sigunguCode != null)
            params.put("sigunguCode", sigunguCode)
        if (eventEndDate != null)
            params.put("eventEndDate", eventEndDate)

        return instance.requestSearchFestival(params)
    }

    /* 소개정보조회로 타입별공통정보기본정보,약도이미지,대표이미지,분류정보,지역정보,주소정보,좌표정보,개요정보,길안내정보,이미지정보,연계관광정보목록을 조회 가능 */
    fun getDetailIntro(contentId: String, contentTypeId: String): Call<DetailIntroVO> {
        var params =  getDefaultParams()

        params.put("contentId", contentId)
        params.put("contentTypeId", contentTypeId)

        return instance.requestDetailIntro(params)
    }

    /* 반복정보조회로 상세소개쉬는날, 개장기간 등 내역을 조회 가능 */
    fun getDetailInfo(contentId: String, contentTypeId: String): Call<DetailInfoVO> {
        var params =  getDefaultParams()

        params.put("contentId", contentId)
        params.put("contentTypeId", contentTypeId)

        return instance.requestDetailInfo(params)
    }
}