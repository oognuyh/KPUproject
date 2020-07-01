package com.fleecyclouds.flipthetripover.sb.cardItem;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fleecyclouds.flipthetripover.R;
import com.fleecyclouds.flipthetripover.sb.festivalDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class cardDetailAdapter extends RecyclerView.Adapter<cardDetailAdapter.ViewHolder> {
    private ArrayList<cardDetailItem> mDataset; //MainActivity에 item class를 정의해 놓았음

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // 사용될 항목들 선언
        public ImageView thumbnail;
        public TextView title;
        public TextView addr1;
        public TextView overView;
        public String contentId;
        public ViewHolder(View v) {
            super(v);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            title = (TextView) v.findViewById(R.id.title);
            addr1 = (TextView) v.findViewById(R.id.addr1);
            overView=(TextView)v.findViewById(R.id.eventstartdate);


            v.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    String contentId = mDataset.get(pos).contentId.toString();

                    final String courseDetail ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?" +
                            "ServiceKey=Hq%2FpOnIAWnAuEq02uMK4seWbjmcsxk7uTSQgXqsjVnD71mFGMBLinIZ1WxhWTzPK6C8dnDCRBGif%2BPPpFhwZZw%3D%3D" +
                            "&contentTypeId=15&contentId=" +contentId+
                            "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y&_type=json";

                    Intent intent = new Intent(v.getContext(), festivalDetail.class);
                    intent.putExtra("url",courseDetail);
                    ((Activity) v.getContext()).startActivity(intent);
                }
            });
        }
    }

    // 생성자 - 넘어 오는 데이터파입에 유의해야 한다.
    public cardDetailAdapter(ArrayList<cardDetailItem> myDataset) {
        mDataset = myDataset;
    }


    //뷰홀더
    // Create new views (invoked by the layout manager)
    @Override
    public cardDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        cardDetailAdapter.ViewHolder holder = new cardDetailAdapter.ViewHolder(v);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(cardDetailAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(mDataset!=null){
            String a = mDataset.get(position).getThumbnail();
            Picasso.get().load(a).into(holder.thumbnail);
            holder.title.setText(mDataset.get(position).getTitle());
            holder.addr1.setText("주소 :"+mDataset.get(position).getAddr1());
            holder.overView.setText("상세 정보 :"+mDataset.get(position).getEventstartdate());
        }
        else{
            holder.title.setText("준비중");
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}