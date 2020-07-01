package com.fleecyclouds.flipthetripover.sb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fleecyclouds.flipthetripover.R;
import com.fleecyclouds.flipthetripover.sb.cardItem.cardCourseAdapter;
import com.fleecyclouds.flipthetripover.sb.cardItem.cardCourseItem;
import com.fleecyclouds.flipthetripover.sb.itemForClass.courseDetail.courseDetail;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class courseDetailActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    TextView textView;
    // 아이템 리스트
    //private String[] myDataset;
    private static ArrayList<cardCourseItem> itemArrayList;
    public static cardCourseItem cardItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Intent get_intent=getIntent();

        String url =get_intent.getStringExtra("url");

        sendRequest(url);
    } public void sendImageRequest(String a, ImageView iv){
        String url =a;
        ImageLoadTask task = new ImageLoadTask(url,iv);
        task.execute();
    }
    //json 을 바이트 형태로 받아옴
    public void sendRequest(String url){

        //코스정보 url


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
        )

        {
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
        //코스 정보를 출력
        Bundle bundle=new Bundle();
        Gson gson =new Gson();
        courseDetail cd = new courseDetail();
        cd = gson.fromJson(response, courseDetail.class);
        ArrayList<cardCourseItem> a=new ArrayList<>() ;
        if(cd!=null){
            String addr;
            int count = (int)cd.response.body.totalCount;

            for (int i= 0 ; i<count ; i++){
                String url = cd.response.body.items.item.get(i).subdetailimg;
                String title =(cd.response.body.items.item.get(i).subname.toString());
                try{
                 addr=cd.response.body.items.item.get(i).subdetailalt.toString();}
                catch(Exception e){
                     addr ="준비중";

                }
                String overView =cd.response.body.items.item.get(i).subdetailoverview.toString();
                a.add(new cardCourseItem(url,title,addr,overView));
            }
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //Linear layout manager 사용
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //어답터 세팅
        mAdapter = new cardCourseAdapter(a); //스트링 배열 데이터 인자로
        mRecyclerView.setAdapter(mAdapter);


    }
    public void println(String data){
        textView.append(data+"\n");
    }

}
