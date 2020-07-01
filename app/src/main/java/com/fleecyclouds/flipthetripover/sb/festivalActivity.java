package com.fleecyclouds.flipthetripover.sb;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fleecyclouds.flipthetripover.R;
import com.fleecyclouds.flipthetripover.sb.cardItem.cardDetailAdapter;
import com.fleecyclouds.flipthetripover.sb.cardItem.cardDetailItem;
import com.fleecyclouds.flipthetripover.sb.itemForClass.festival.FestivalList;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class festivalActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // 아이템 리스트
    //private String[] myDataset;
    private static ArrayList<cardDetailItem> itemArrayList;
    public static cardDetailItem cardItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival);

        itemArrayList = new ArrayList<>();

        sendRequest();



    }


    public void sendRequest(){
        // 축제정보 url 20200420 부터 출력하도록 함,
        String url ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?" +
                "serviceKey=Hq%2FpOnIAWnAuEq02uMK4seWbjmcsxk7uTSQgXqsjVnD71mFGMBLinIZ1WxhWTzPK6C8dnDCRBGif%2BPPpFhwZZw%3D%3D" +
                "&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&arrange=A&listYN=Y&areaCode=&sigunguCode=" +
                "&eventStartDate=20200601&eventEndDate=&modifiedtime=&_type=json";

        //"https://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival" +
        //"?serviceKey=Hq%2FpOnIAWnAuEq02uMK4seWbjmcsxk7uTSQgXqsjVnD71mFGMBLinIZ1WxhWTzPK6C8dnDCRBGif%2BPPpFhwZZw%3D%3D" +
        //"&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&arrange=A&listYN=Y&eventStartDate=20200412&_type=json";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            String response2 = new String(response.getBytes("ISO-8859-1"),"UTF-8");
                            processResponse(response2);


                        }
                        catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    public void onErrorResponse(VolleyError error){
                        println("에러 ->"+
                                error.getMessage());

                    }

                }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        //매번 새로운 것을 보여주기 위함임
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

    }


    public void processResponse(String response){
        Bundle bundle=new Bundle();
        Gson gson =new Gson();
        FestivalList ml = new FestivalList();
        ml = gson.fromJson(response, FestivalList.class);
        String startdate;
        ArrayList<cardDetailItem> a=new ArrayList<>() ;
        if(ml!=null){
            int count = ml.response.body.items.item.size();

            for (int i= 0 ; i<10 ; i++){
                String url =ml.response.body.items.item.get(i).firstimage;
                String title =(ml.response.body.items.item.get(i).title.toString());
                String addr=ml.response.body.items.item.get(i).addr1.toString();

                startdate =ml.response.body.items.item.get(i).eventstartdate.toString();

                String contentId = ml.response.body.items.item.get(i).contentid.toString();
                itemArrayList.add(new cardDetailItem(url,title,addr,startdate,contentId));
            }
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);//옵션
        //Linear layout manager 사용
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //어답터 세팅
        mAdapter = new cardDetailAdapter(itemArrayList); //스트링 배열 데이터 인자로
        mRecyclerView.setAdapter(mAdapter);

    }
    public void println(String data){
        Log.e("error",data);
    }

}