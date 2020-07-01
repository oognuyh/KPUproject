package com.fleecyclouds.flipthetripover.data

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

// TODO VO 파일 나누기

/* AreaBasedList VO*/
data class AreaBasedVO(@SerializedName("response") val response: AreaBasedResponse)
data class AreaBasedResponse(@SerializedName("header") val header: AreaBasedHeader, @SerializedName("body") val body: AreaBasedBody)
data class AreaBasedHeader(@SerializedName("resultCode") val resultCode: String, @SerializedName("resultMsg") val resultMsg: String)
data class AreaBasedBody(@SerializedName("items") val box: AreaBasedBox, @SerializedName("numOfRows") val numOfRows: Long, @SerializedName("pageNo") val pageNo: Long, @SerializedName("totalCount") val totalCount: Long)
data class AreaBasedBox(@SerializedName("item") val contents: List<AreaBasedContent>)
data class AreaBasedContent(
    val addr1: String?, // 주소
    val addr2: String?, // 상세주소
    val areacode: Long?, // 지역코드
    val cat1: String?, // 대분류
    val cat2: String?, // 중분류
    val cat3: String?, // 소분류
    val contentid: Long, // 콘텐츠ID
    val contenttypeid: Long, // 콘텐츠타입ID
    val createdtime: Long, // 등록일
    val firstimage: String?, // 대표이미지(원본)
    val firstimage2: String?, // 대표이미지(썸네일)
    val mapx: Double?, // GPS X좌표
    val mapy: Double?, // GPS Y좌표
    val mlevel: Int?, // MAP LEVEL
    val modifiedtime: Long, // 수정일
    val readcount: Long?, // 조회수
    val sigungucode: Long?, // 시군구코드
    val tel: String?, // 전화번호
    val title: String, // 제목
    val booktour: Int?, // 교과서 속 여행지여부
    val zipcode: String? // 우편번호
)

/* LocationBasedList VO */
data class LocationBasedVO(@SerializedName("response") val response: LocationBasedResponse)
data class LocationBasedResponse(@SerializedName("header") val header: LocationBasedHeader, @SerializedName("body") val body: LocationBasedBody)
data class LocationBasedHeader(@SerializedName("resultCode") val resultCode: String, @SerializedName("resultMsg") val resultMsg: String)
data class LocationBasedBody(@SerializedName("items") val box: LocationBasedBox, @SerializedName("numOfRows") val numOfRows: Long, @SerializedName("pageNo") val pageNo: Long, @SerializedName("totalCount") val totalCount: Long)
data class LocationBasedBox(@SerializedName("item") val contents: List<LocationBasedContent>)
data class LocationBasedContent (
    val addr1: String?, // 주소
    val addr2: String?, // 상세주소
    val areacode: Long?, // 지역코드
    val booktour: Int?, // 교과서 속 여행지여부
    val cat1: String?, // 대분류
    val cat2: String?, // 중분류
    val cat3: String?, // 소분류
    val contentid: Long, // 콘텐츠ID
    val contenttypeid: Long, // 콘텐츠타입ID
    val createdtime: Long, // 등록일
    val firstimage: String?, // 대표이미지(원본)
    val firstimage2: String?, // 대표이미지(썸네일)
    val mapx: Double?, // GPS X좌표
    val mapy: Double?, // GPS Y좌표
    val mlevel: Int?, // MAP LEVEL
    val modifiedtime: Long, // 수정일
    val readcount: Long?, // 조회수
    val sigungucode: Long?, // 시군구코드
    val tel: String?, // 전화번호
    val title: String, // 제목
    val dist: Long? // 중심 좌표로부터 거리(단위 m)
)

/* DetailCommon VO */
data class DetailCommonVO(@SerializedName("response") val response: DetailCommonResponse)
data class DetailCommonResponse(@SerializedName("header") val header: DetailCommonHeader, @SerializedName("body") val body: DetailCommonBody)
data class DetailCommonHeader(@SerializedName("resultCode") val resultCode: String, @SerializedName("resultMsg") val resultMsg: String)
data class DetailCommonBody(@SerializedName("items") val box: DetailCommonBox, @SerializedName("numOfRows") val numOfRows: Long, @SerializedName("pageNo") val pageNo: Long, @SerializedName("totalCount") val totalCount: Long)
data class DetailCommonBox(@SerializedName("item") val contents: DetailCommonContent)
data class DetailCommonContent (
    val contentid: Long, // 콘텐츠ID
    val contenttypeid: Long?, // 콘텐츠타입ID
    val booktour: Int?, // 교과서 속 여행지여부
    val createdtime: Long, // 등록일
    val homepage: String?, // 홈페이지 주소
    val modifiedtime: Long, // 수정일
    val tel: String?, // 전화번호
    val telname: String?, // 전화번호명
    val firstimage: String?, // 대표이미지(원본)
    val firstimage2: String?, // 대표이미지(썸네일)
    val areacode: Long?, // 지역코드
    val sigungucode: Long?, // 시군구코드
    val cat1: String?, // 대분류
    val cat2: String?, // 중분류
    val cat3: String?, // 소분류
    val addr1: String?, // 주소
    val addr2: String?, // 상세주소
    val zipcode: String?, // 우편번호
    val mapx: Double?, // GPS X좌표
    val mapy: Double?, // GPS Y좌표
    val mlevel: Int?, // MAP LEVEL
    val overview: String?, // 콘텐츠 개요 조회
    val title: String // 제목
)

/* SearchFestival VO */
data class SearchFestivalVO(@SerializedName("response") val response: SearchFestivalResponse)
data class SearchFestivalResponse(@SerializedName("header") val header: SearchFestivalHeader, @SerializedName("body") val body: SearchFestivalBody)
data class SearchFestivalHeader(@SerializedName("resultCode") val resultCode: String, @SerializedName("resultMsg") val resultMsg: String)
data class SearchFestivalBody(@SerializedName("items") val box: SearchFestivalBox, @SerializedName("numOfRows") val numOfRows: Long, @SerializedName("pageNo") val pageNo: Long, @SerializedName("totalCount") val totalCount: Long)
data class SearchFestivalBox(@SerializedName("item") val contents: List<SearchFestivalContent>)
data class SearchFestivalContent (
    val addr1: String?, // 주소
    val addr2: String?, // 상세주소
    val areacode: Long?, // 지역코드
    val booktour: Int?, // 교과서 속 여행지여부
    val cat1: String?, // 대분류
    val cat2: String?, // 중분류
    val cat3: String?, // 소분류
    val contentid: Long, // 콘텐츠ID
    val contenttypeid: Long, // 콘텐츠타입ID
    val createdtime: Long, // 등록일
    val firstimage: String?, // 대표이미지(원본)
    val firstimage2: String?, // 대표이미지(썸네일)
    val mapx: Double?, // GPS X좌표
    val mapy: Double?, // GPS Y좌표
    val mlevel: Int?, // MAP LEVEL
    val modifiedtime: Long, // 수정일
    val readcount: Long?, // 조회수
    val sigungucode: Long?, // 시군구코드
    val tel: String?, // 전화번호
    val title: String, // 제목
    val eventstartdate: String?, // 행사 시작일(축제 정보 요청 시)
    val eventenddate: String // 행사 종료일(축제 정보 요청 시)
)

/* DetailInfo VO*/
data class DetailInfoVO(@SerializedName("response") val response: DetailInfoResponse)
data class DetailInfoResponse(@SerializedName("header") val header: DetailInfoHeader, @SerializedName("body") val body: DetailInfoBody)
data class DetailInfoHeader(@SerializedName("resultCode") val resultCode: String, @SerializedName("resultMsg") val resultMsg: String)
data class DetailInfoBody(@SerializedName("items") val box: DetailInfoBox, @SerializedName("numOfRows") val numOfRows: Long, @SerializedName("pageNo") val pageNo: Long, @SerializedName("totalCount") val totalCount: Long)
data class DetailInfoBox(@SerializedName("item") val contents: Any)
data class DetailInfoContent (
    val contentid: Long, // 콘텐츠ID
    val contenttypeid: Long, // 콘텐츠타입ID
    val fldgubun: Long?, // 일련번호
    val infoname: String?, // 제목
    val infotext: String?, // 내용
    val serialnum: Long?, // 반복 일련번호
    val subcontentid: Long?, // 하위 콘텐츠ID
    val subdetailalt: String?, // 코스이미지 설명
    val subdetailimg: String?, // 코스이미지
    val subdetailoverview: String?, // 코스개요
    val subname: String?, // 코스명
    val subnum: Long?, // 반복 일련번호
    val roomcode: String?, // 객실코드
    val roomtitle: String?, // 객실명칭
    val roomsize1: String?, // 객실크기(평)
    val roomcount: String?, // 객실수
    val roombasecount: String?, // 기준인원
    val roommaxcount: String?, // 최대인원
    val roomoffseasonminfee1: String?, // 비수기주중최소
    val roomoffseasonminfee2: String?, // 비수기주말최소
    val roompeakseasonminfee1: String?, // 성수기주중최소
    val roompeakseasonminfee2: String?, // 성수기주말최소
    val roomintro: String?, // 객실소개
    val roombathfacility: String?, // 목욕시설 여부
    val roombath: String?, // 욕조 여부
    val roomhometheater: String?, // 홈시어터 여부
    val roomaircondition: String?, // 에어컨 여부
    val roomtv: String?, // TV 여부
    val roompc: String?, // PC
    val roomcable: String?, // 케이블설치 여부
    val roominternet: String?, // 인터넷 여부
    val roomrefrigerator: String?, // 냉장고 여부
    val roomtoiletries: String?, // 세면도구 여부
    val roomsofa: String?, // 소파 여부
    val roomcook: String?, // 취사용품 여부
    val roomtable: String?, // 테이블 여부
    val roomhairdryer: String?, // 드라이기 여부
    val roomsize2: String?, // 객실크기(평방미터)
    val roomimg1: String?, // 객실사진1
    val roomimg1alt: String?, // 객실사진1 설명
    val roomimg2: String?, // 객실사진2
    val roomimg2alt: String?, // 객실사진2 설명
    val roomimg3: String?, // 객실사진3
    val roomimg3alt: String?, // 객실사진3 설명
    val roomimg4: String?, // 객실사진4
    val roomimg4alt: String?, // 객실사진4 설명
    val roomimg5: String?, // 객실사진5
    val roomimg5alt: String? // 객실사진5 설명
)

// TOUR API DetailIntro 요청 시, item 항목이 동적으로 역직렬화 필요
class DetailInfoDeserializer : JsonDeserializer<DetailInfoBox> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): DetailInfoBox? {
        json as JsonObject

        with (json.get("item")) {
            return if (isJsonArray) { // jsonArray 형식으로 들어온 경우
                val type = object : TypeToken<List<DetailInfoContent>>() {}.type
                DetailInfoBox(Gson().fromJson<List<DetailInfoContent>>(asJsonArray, type))
            } else { // jsonObject 형식으로 들어온 경우
                val type = object : TypeToken<DetailInfoContent>() {}.type
                DetailInfoBox(Gson().fromJson<DetailInfoContent>(asJsonObject, type))
            }
        }
    }

}

/* DetailIntro VO */
data class DetailIntroVO(@SerializedName("response") val response: DetailIntroResponse)
data class DetailIntroResponse(@SerializedName("header") val header: DetailIntroHeader, @SerializedName("body") val body: DetailIntroBody)
data class DetailIntroHeader(@SerializedName("resultCode") val resultCode: String, @SerializedName("resultMsg") val resultMsg: String)
data class DetailIntroBody(@SerializedName("items") val box: DetailIntroBox, @SerializedName("numOfRows") val numOfRows: Long, @SerializedName("pageNo") val pageNo: Long, @SerializedName("totalCount") val totalCount: Long)
data class DetailIntroBox(@SerializedName("item") val contents: DetailIntroContent)
data class DetailIntroContent (
    val contentid: Long, // 콘텐츠ID
    val contenttypeid: Long, // 콘텐츠타입ID

    val accomcount: String?, // 수용인원
    val chkbabycarriage: String?, // 유모차대여 정보
    val chkcreditcard: String?, // 신용카드가능 정보
    val chkpet: String?, // 전화번호
    val expagerange: String?, // 체험가능 연령
    val expguide: String?, // 체험안내
    val heritage1: String?, // 세계 문화유산 유무
    val heritage2: String?, // 세계 자연유산 유무
    val heritage3: String?, // 세계 기록유산 유무
    val infocenter: String?, // 문의 및 안내
    val opendate: String?, // 개장일
    val parking: String?, // 주차시설
    val restdate: String?, // 쉬는날
    val useseason: String?, // 이용시기
    val usetime: String?, // 이용시간
    val accomcountculture: String?, // 수용인원
    val chkbabycarriageculture: String?, // 유모차대여 정보
    val chkcreditcardculture: String?, // 신용카드가능 정보
    val chkpetculture: String?, // 애완동물동반가능 정보
    val discountinfo: String?, // 할인정보
    val infocenterculture: String?, // 문의 및 안내
    val parkingculture: String?, // 주차시설
    val parkingfee: String?, // 주차요금
    val restdateculture: String?, // 쉬는날
    val usefee: String?, // 이용요금
    val usetimeculture: String?, // 이용시간
    val scale: String?, // 규모
    val spendtime: String?, // 관람 소요시간
    val agelimit: String?, // 관람 가능연령
    val bookingplace: String?, // 예매처
    val discountinfofestival: String?, // 할인정보
    val eventEndDate: String?, // 행사 종료일
    val eventhomepage: String?, // 행사 홈페이지
    val eventplace: String?, // 행사 장소
    val eventStartDate: String?, // 행사 시작일
    val festivalgrade: String?, // 축제등급
    val placeinfo: String?, // 행사장 위치안내
    val playtime: String?, // 공연시간
    val program: String?, // 행사 프로그램
    val spendtimefestival: String?, // 관람 소요시간
    val sponsor1: String?, // 주최자 정보
    val sponsor1tel: String?, // 주최자 연락처
    val sponsor2: String?, // 주관사 정보
    val sponsor2tel: String?, // 주관사 연락처
    val subevent: String?, // 부대행사 내용
    val usetimefestival: String?, // 이용요금

    val distance: String?, // 코스 총거리 (신규항목)
    val infocentertourcourse: String?, // 문의 및 안내
    val schedule: String?, // 코스 일정
    val taketime: String?, // 코스 총 소요 시간 (신규항목)
    val theme: String?, // 코스 테마

    val accomcountleports: String?, // 수용인원
    val chkbabycarriageleports: String?, // 유모차대여 정보
    val chkcreditcardleports: String?, // 신용카드가능 정보
    val chkpetleports: String?, // 애완동물동반가능 정보
    val expagerangeleports: String?, // 체험 가능연령
    val infocenterleports: String?, // 문의 및 안내
    val openperiod: String?, // 개장기간
    val parkingfeeleports: String?, // 주차요금
    val parkingleports: String?, // 주차시설
    val reservation: String?, // 예약안내
    val restdateleports: String?, // 쉬는날
    val scaleleports: String?, // 규모
    val usefeeleports: String?, // 입장료
    val usetimeleports: String?, // 이용시간
    val accomcountlodging: String?, // 수용 가능인원
    val benikia: String?, // 베니키아 여부
    val chekintime: String?, // 입실 시간
    val checkouttime: String?, // 퇴실 시간
    val chkcooking: String?, // 객실내 취사 여부
    val foodplace: String?, // 식음료장
    val goodstay: String?, // 굿스테이 여부
    val hanok: String?, // 한옥 여부
    val infocenterlodging: String?, // 문의 및 안내
    val parkingloding: String?, // 주차시설
    val pickup: String?, // 픽업 서비스
    val roomcount: String?, // 객실수
    val reservationlodging: String?, // 예약안내
    val reservationurl: String?, // 예약안내 홈페이지
    val roomtype: String?, // 객실유형
    val scalelodging: String?, // 규모
    val subfacility: String?, // 부대시설 (기타)
    val barbecue: String?, // 부대시설 정보
    val beauty: String?, // 부대시설 정보
    val beverage: String?, // 부대시설 정보
    val bicycle: String?, // 부대시설 정보
    val campfire: String?, // 부대시설 정보
    val fitness: String?, // 부대시설 정보
    val karaoke: String?, // 부대시설 정보
    val publicbath: String?, // 부대시설 정보
    val publicpc: String?, // 부대시설 정보
    val sauna: String?, // 부대시설 정보
    val seminar: String?, // 부대시설 정보
    val sports: String?, // 부대시설 정보

    val chkbabycarriageshopping: String?, // 유모차대여 정보
    val chkcreditcardshopping: String?, // 신용카드가능 정보
    val chkpetshopping: String?, // 애완동물동반가능 정보
    val culturecenter: String?, // 문화센터 바로가기
    val fairday: String?, // 장서는 날
    val infocentershopping: String?, // 문의 및 안내
    val opendateshopping: String?, // 개장일
    val opentime: String?, // 영업시간
    val parkingshopping: String?, // 주차시설
    val restdateshopping: String?, // 쉬는날
    val restroom: String?, // 화장실 설명
    val saleitem: String?, // 판매 품목
    val saleitemcost: String?, // 판매 품목별 가격
    val scaleshopping: String?, // 규모
    val shopguide: String?, // 매장안내

    val chkcreditcrdfood: String?, // 신용카드가능 정보
    val discountinfofood: String?, // 할인정보
    val firstmenu: String?, // 대표 메뉴
    val infocenterfood: String?, // 문의 및 안내
    val kidsfacility: String?, // 어린이 놀이방 여부
    val opendatefood: String?, // 개업일
    val opentimefood: String?, // 영업시간
    val packing: String?, // 포장 가능
    val parkingfood: String?, // 주차시설
    val reservationfood: String?, // 예약안내
    val restdatefood: String?, // 쉬는날
    val scalefood: String?, // 규모
    val seat: String?, // 좌석수
    val smoking: String?, // 금연/흡연 여부
    val treatmenu: String?, // 취급 메뉴
    val refundregulation: String?, // 환불규정
    val lcnsno: String? // 인허가번호
)
