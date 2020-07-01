package com.fleecyclouds.flipthetripover.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.fleecyclouds.flipthetripover.R;
import com.fleecyclouds.flipthetripover.sb.AppHelper;
import com.fleecyclouds.flipthetripover.sb.ImageLoadTask;
import com.fleecyclouds.flipthetripover.sb.courseDetailActivity;
import com.fleecyclouds.flipthetripover.sb.festivalActivity;
import com.fleecyclouds.flipthetripover.sb.itemForClass.course.course;
import com.fleecyclouds.flipthetripover.sb.itemForClass.festival.FestivalList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    /*
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // 아이템 리스트
    //private String[] myDataset;
    private static ArrayList<com.example.projcetm1.cardItem.cardItem> itemArrayList;
    public static cardItem cardItem;

     */
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Button btnRevoke, btnLogout;
    private FirebaseAuth mAuth ;
    private FestivalList response;
    TextView textView;

    ImageView imageView;
    TextView courseTitle;
    TextView courseTitle2;

    ImageView courseIv;
    ImageView courseIv2;

    Button courseInfo;
    Button courseInfo2;
    Button festivalDetailInfo;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        inflater.inflate(R.layout.fragment_home, container, false);

        View v = inflater.inflate(R.layout.fragment_home,container,false);

        // 축제정보 url 20200420 부터 출력하도록 함,


        FestivalList fl=new FestivalList();
        textView =(TextView) v.findViewById(R.id.course_title2);
        //  imageView=(ImageView) v.findViewById(R.id.imageView);
        courseIv=(ImageView) v.findViewById(R.id.course_iv);
        courseIv2=(ImageView) v.findViewById(R.id.course_iv2);
        courseTitle2=(TextView)v.findViewById(R.id.course_title2);
        courseTitle=(TextView) v.findViewById(R.id.course_title);
        courseInfo = (Button) v.findViewById(R.id.course_button);
        courseInfo2 = (Button) v.findViewById(R.id.course_button2);

        //festivalDetailInfo =(Button)v.findViewById(R.id.festivalDetailInfo);
        final ImageButton festivalInfo = (ImageButton)v.findViewById(R.id.festivalInfo);

        festivalInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), festivalActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

//        btnLogout.setOnClickListener(this);

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }
        sendRequest();
        sendRequest2();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void sendImageRequest(String a,ImageView iv){
        //String url="https://movie-phinf.pstatic.net/20200130_198/15803497537218LnrU_JPEG/movie_image.jpg?type=m665_443_2";
        String url =a;
        Picasso.get().load(a).into(iv);

    }
    //json 을 바이트 형태로 받아옴
    public void sendRequest(){
        String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?" +
                "serviceKey=Hq%2FpOnIAWnAuEq02uMK4seWbjmcsxk7uTSQgXqsjVnD71mFGMBLinIZ1WxhWTzPK6C8dnDCRBGif%2BPPpFhwZZw%3D%3D" +
                "&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&arrange=A&listYN=Y&areaCode=&sigunguCode=" +
                "&eventStartDate=20200601&eventEndDate=&modifiedtime=&_type=json";
        //코스정보 url
        String urlCourse="http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList" +
                "?ServiceKey=Hq%2FpOnIAWnAuEq02uMK4seWbjmcsxk7uTSQgXqsjVnD71mFGMBLinIZ1WxhWTzPK6C8dnDCRBGif%2BPPpFhwZZw%3D%3D" +
                "&contentTypeId=25&areaCode=&sigunguCode=&cat1=C01&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo=1&_type=json";

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
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
        };
        //매번 새로운 것을 보여주기 위함임
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);


    }
    public void sendRequest2(){

        //코스정보 url
        String urlCourse="http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList" +
                "?ServiceKey=Hq%2FpOnIAWnAuEq02uMK4seWbjmcsxk7uTSQgXqsjVnD71mFGMBLinIZ1WxhWTzPK6C8dnDCRBGif%2BPPpFhwZZw%3D%3D" +
                "&contentTypeId=25&areaCode=&sigunguCode=&cat1=C01&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo=1&_type=json";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                urlCourse,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            String response2 = new String(response.getBytes("ISO-8859-1"),"UTF-8");
                            processResponse2(response2);
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
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
        };
        //매번 새로운 것을 보여주기 위함임
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);


    }
    //json to class 으로 정제함
    public void processResponse(String response){
        List<SlideModel> slideModels = new ArrayList<>();

        Bundle bundle=new Bundle();
        Gson gson =new Gson();
        FestivalList ml = new FestivalList();
        ml = gson.fromJson(response, FestivalList.class);
        if(ml!=null){
            ImageSlider imageSlider=getActivity().findViewById(R.id.slider);

            int count = ml.response.body.items.item.size();
            //축제정보를 랜덤으로 띄어줌


            for (int i= 0 ; i<10 ; i++){
                String title =(ml.response.body.items.item.get(i).title.toString());
                String url =ml.response.body.items.item.get(i).firstimage;

                if(url !=null)
                    slideModels.add(new SlideModel(url,title));

            }
            imageSlider.setImageList(slideModels, true);


        }
    }


    public void processResponse2(String response){
        Bundle bundle=new Bundle();
        Gson gson =new Gson();
        course cl = new course();
        cl = gson.fromJson(response, course.class);
        if(cl!=null){

            //코스정보를 랜덤으로 띄어줌
            int i = new Random().nextInt(5);
            int j = new Random().nextInt(10);
            String url =cl.response.body.items.item.get(i).firstimage;
            String url2 =cl.response.body.items.item.get(j).firstimage;
            sendImageRequest(url,courseIv);
            sendImageRequest(url2,courseIv2);

            courseTitle.setText(cl.response.body.items.item.get(i).title.toString());
            courseTitle2.setText(cl.response.body.items.item.get(j).title.toString());

            String contentId = cl.response.body.items.item.get(i).contentid.toString();
            String contentId2 = cl.response.body.items.item.get(j).contentid.toString();

            final String courseDetail ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo?" +
                    "ServiceKey=Hq%2FpOnIAWnAuEq02uMK4seWbjmcsxk7uTSQgXqsjVnD71mFGMBLinIZ1WxhWTzPK6C8dnDCRBGif%2BPPpFhwZZw%3D%3D&contentTypeId=25&" +
                    "contentId="+contentId+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y&_type=json";
            final String courseDetail2 ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo?" +
                    "ServiceKey=Hq%2FpOnIAWnAuEq02uMK4seWbjmcsxk7uTSQgXqsjVnD71mFGMBLinIZ1WxhWTzPK6C8dnDCRBGif%2BPPpFhwZZw%3D%3D" +
                    "&contentTypeId=25&contentId="+contentId2+
                    "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y&_type=json";

            courseInfo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(getActivity(), courseDetailActivity.class);
                    intent.putExtra("url",courseDetail);
                    startActivity(intent);
                }
            });
            courseInfo2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(getActivity(), courseDetailActivity.class);
                    intent.putExtra("url",courseDetail2);
                    startActivity(intent);
                }
            });
        }

    }
    //코스정보 정제

    public void println(String data){
        textView.append(data+"\n");
    }




}
