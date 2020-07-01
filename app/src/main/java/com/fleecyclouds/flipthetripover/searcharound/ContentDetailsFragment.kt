package com.fleecyclouds.flipthetripover.searcharound

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fleecyclouds.flipthetripover.data.*
import com.fleecyclouds.flipthetripover.R
import com.fleecyclouds.flipthetripover.utils.RecommedAPIHelper
import com.fleecyclouds.flipthetripover.utils.Recommended
import com.fleecyclouds.flipthetripover.utils.TourAPIHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.squareup.picasso.Picasso
import com.unity3d.player.UnityPlayerActivity
import kotlinx.android.synthetic.main.fragment_content_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* 주변관광지찾기 -> 상세정보조회 */
/* 플래너 -> 상세정보조회 */
/* 12 관광지 14 문화시설 15 축제 28 레포츠 25 여행코스 38 쇼핑 */
// TODO 유사한 관광지 추천 기능
// TODO 넘겨받은 콘텐츠ID, 콘텐츠타입ID를 통해 DetailCommon 조회

class ContentDetailsFragment : Fragment() {
    private val TAG = "ContentDetail"

    private lateinit var contentId: String
    private lateinit var contentTypeId: String
    private lateinit var longitude: String
    private lateinit var latitude: String

    private lateinit var adapter: ContentDetailsRecycelrViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_content_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO 파라미터 수신
        contentId = arguments?.getString("contentId")!!
        contentTypeId = arguments?.getString("contentTypeId")!!
        longitude = arguments?.getString("longitude")!!
        latitude = arguments?.getString("latitude")!!
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        content_details_rcv_recommend.layoutManager = linearLayoutManager
        getRecommended()
        content_details_tmap.setSKTMapApiKey(getString(R.string.TMAP_API_KEY))



        var comment:String

        val commentbtn = view.findViewById<Button>(R.id.comment_btn)
        val editText = view.findViewById<EditText>(R.id.comment_input)
        val dR : DatabaseReference = FirebaseDatabase.getInstance().getReference()
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference =database.getReference("comment")
        val myRef2 : DatabaseReference=database.getReference("score")
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val name = user.displayName

            val uid = user.uid

            commentbtn.setOnClickListener{
                val context =HashMap<String,String>()
                val context2 = HashMap<String,String>()
                comment = editText.text.toString()

                context.put("comment",comment)
                context.put("name",name.toString())

                context2.put("comment",comment)

                myRef.child(contentId).child(uid)
                    .setValue(context)
                myRef2.child(uid).child(contentId).setValue(context2)

                Toast.makeText(getContext(),"댓글이 입력되었습니다.", Toast.LENGTH_SHORT).show()
                editText.setText(null)
            }
        }
        val list =view.findViewById<ListView>(R.id.comment_list)
        val hash = HashMap<String,String>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            val arrays: MutableList<MutableMap<String,String>> = mutableListOf()

            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.child(contentId).children) {
                    val a :String = snapshot.key.toString()
                    val comments:String =snapshot.child("comment").getValue().toString()
                    val names :String =snapshot.child("name").getValue().toString()
                    if(comments !=null){
                        var map = mutableMapOf("names" to names,"comments" to comments)

                        arrays.add(map)}



                }

                val from = arrayOf("names","comments")
                val to =intArrayOf(android.R.id.text1,android.R.id.text2)

                list.adapter=
                    SimpleAdapter(context,arrays,android.R.layout.simple_list_item_2,from, to)
            }
        })







        getDetailCommon(view)
        getDetailIntro(view)
        getDetailInfo(view)
    }

    private fun getContentDetails(contents: ArrayList<String>) {
        if (contents.size == 0)
            return

        val res = TourAPIHelper.getDetailCommon(contentId = contents.get(0))
        res.enqueue(object : Callback<DetailCommonVO>{
            override fun onFailure(call: Call<DetailCommonVO>, t: Throwable) {
                Log.d(TAG, "Failed to get recommended2")
                Log.d(TAG, t.message)
            }

            override fun onResponse(call: Call<DetailCommonVO>, response: Response<DetailCommonVO>) {
                val content = response.body()?.response?.body?.box?.contents
                Log.d(TAG, content.toString())
                content?.let { it1 -> adapter.addContent(it1) }

            }
        })
        contents.removeAt(0)
        Log.d(TAG, contents.toString())
        getContentDetails(contents)
    }

    private fun getRecommended() {
        // todo 수정필요
        val recommended = ArrayList<DetailCommonContent>()
        adapter = ContentDetailsRecycelrViewAdapter(recommended)
        content_details_rcv_recommend.adapter = adapter
        var contents = ArrayList<String>()
        val response = RecommedAPIHelper.getRecommended(contentId)
        response.enqueue(object : Callback<Recommended> {
            override fun onFailure(call: Call<Recommended>, t: Throwable) {
                Log.d(TAG, "Failed to get recommended")
                Log.d(TAG, t.message)
            }

            override fun onResponse(call: Call<Recommended>, response: Response<Recommended>) {
                Log.d(TAG, response.body().toString())
                response.body()?.recommended?.map {
                    contents.add(it)
                }
                getContentDetails(contents)
            }
        })
    }

    private fun getDetailCommon(view: View) {
        /* 주소, 대표 이미지, 홈페이지 주소(html 형식), 좌표, 개요, 제목, 우편번호 등을 받을 수 있음*/
        val response = TourAPIHelper.getDetailCommon(contentId = contentId, contentTypeId = contentTypeId)
        val title = view.findViewById<TextView>(R.id.content_details_title)
        val overview = view.findViewById<TextView>(R.id.content_details_overview)
        val thumbnail = view.findViewById<ImageView>(R.id.content_details_thumbnail)

        response.enqueue(object : Callback<DetailCommonVO> {
            override fun onFailure(call: Call<DetailCommonVO>, t: Throwable) {
                Log.d(TAG, "Failed to get DetailCommonVO")
                Log.d(TAG, t.message)
            }
            override fun onResponse(call: Call<DetailCommonVO>, response: Response<DetailCommonVO>) {
                if (response.isSuccessful){
                    with (response.body()?.response?.body?.box?.contents) {
                        val tMapMarkerItem = TMapMarkerItem()
                        tMapMarkerItem.setPosition(0.5f, 1.0f)
                        tMapMarkerItem.tMapPoint = TMapPoint(this?.mapy!!, this.mapx!!)
                        tMapMarkerItem.name = this.title
                        content_details_tmap.addMarkerItem(this.contentid.toString(), tMapMarkerItem)
                        content_details_tmap.setCenterPoint(mapx, mapy)
                        content_details_btn_unity.setOnClickListener {
                            activity?.let {
                                val intent = Intent(requireContext(), UnityPlayerActivity::class.java)
                                val coordinates = longitude + "-" + latitude + "-" + this.mapx + "-" + this.mapy
                                intent.putExtra("coordinates", coordinates)
                                startActivity(intent)
                            }
                        }
                        this.title.let { title.text = it }
                        this.overview.let {
                            overview.linksClickable = true
                            overview.movementMethod = LinkMovementMethod.getInstance()
                            overview.text = Html.fromHtml(it, Build.VERSION.SDK_INT)
                        } // TODO Html.fromHtml SDK VERSION에 따라 파라미터 추가 필요
                        if (this.firstimage == null)
                            thumbnail.visibility = View.GONE
                        else
                            Picasso.get().load(this.firstimage).error(R.drawable.no_image).fit().into(thumbnail)
                    }
                }
            }
        })
    }

    // TODO 텍스트 출력 정리
    private fun getDetailIntro(view: View) {
        /* 수용인원, 주차시설, 쉬는날, 체험가능 연령 등등 세부내용 */
        val response = TourAPIHelper.getDetailIntro(contentId = contentId, contentTypeId = contentTypeId)
        response.enqueue(object : Callback<DetailIntroVO> {
            override fun onFailure(call: Call<DetailIntroVO>, t: Throwable) {
                Log.d(TAG, "Failed to get DetailIntroVO")
                Log.d(TAG, t.message)
            }
            override fun onResponse(call: Call<DetailIntroVO>, response: Response<DetailIntroVO>) {
                with (response.body()?.response?.body?.box?.contents) {
                    Log.d(TAG, this.toString())
                }
            }
        })
    }

    private fun getDetailInfo(view: View) {
        /* 상세소개 쉬는날, 개장기간 등 내역을 조회하는 기능 */
        val detailsLinearLayout = view.findViewById<LinearLayout>(R.id.content_details_details)
        val response = TourAPIHelper.getDetailInfo(contentId = contentId, contentTypeId = contentTypeId)

        response.enqueue(object : Callback<DetailInfoVO> {
            override fun onFailure(call: Call<DetailInfoVO>, t: Throwable) {
                Log.d(TAG, "Failed to get DetailInfoVO")
                Log.d(TAG, t.message)
            }
            // TODO 코드 개선 필요
            override fun onResponse(call: Call<DetailInfoVO>, response: Response<DetailInfoVO>) {
                if (response.isSuccessful) {
                    with (response.body()?.response?.body?.box?.contents) {
                        if (this is List<*>) {
                            val json = Gson().toJson(this)
                            val jsonArray = JsonParser().parse(json).asJsonArray
                            jsonArray.forEach {
                                // TODO parsing infoname and infotext
                                if (it.asJsonObject.has("infoname")) {
                                    val linearLayout = LinearLayout(view.context) // LinearLayout 생성
                                    linearLayout.orientation = LinearLayout.HORIZONTAL // 수직 설정

                                    val infoName = TextView(view.context)
                                    val infoText = TextView(view.context) // TextView 생성

                                    infoName.text = it.asJsonObject.get("infoname").asString
                                    infoText.text = it.asJsonObject.get("infotext").asString

                                    linearLayout.addView(infoName)
                                    linearLayout.addView(infoText)

                                    detailsLinearLayout.addView(linearLayout)
                                }
                            }
                        }
                        else {
                            val json = Gson().toJson(this)
                            val jsonObject = JsonParser().parse(json).asJsonObject

                            if (jsonObject.has("infoname")) {
                                val linearLayout = LinearLayout(view.context) // LinearLayout 생성
                                linearLayout.orientation = LinearLayout.HORIZONTAL // 수직 설정

                                val infoName = TextView(view.context)
                                val infoText = TextView(view.context) // TextView 생성

                                infoName.text = jsonObject.get("infoname").asString
                                infoText.text = jsonObject.get("infotext").asString

                                linearLayout.addView(infoName)
                                linearLayout.addView(infoText)

                                detailsLinearLayout.addView(linearLayout)
                            }
                        }
                    }
                }
            }
        })
    }

}