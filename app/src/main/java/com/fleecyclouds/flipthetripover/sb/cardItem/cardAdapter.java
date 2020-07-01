package com.fleecyclouds.flipthetripover.sb.cardItem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fleecyclouds.flipthetripover.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class cardAdapter extends RecyclerView.Adapter<cardAdapter.ViewHolder> {

    private ArrayList<cardItem> mDataset; //MainActivity에 item class를 정의해 놓았음

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 사용될 항목들 선언
        public ImageView thumbnail;
        public TextView title;
        public TextView addr1;
        public TextView eventstartdate;

        public ViewHolder(View v) {
            super(v);

            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            title = (TextView) v.findViewById(R.id.title);
            addr1 = (TextView) v.findViewById(R.id.addr1);
            eventstartdate=(TextView)v.findViewById(R.id.eventstartdate);
        }
    }

    // 생성자 - 넘어 오는 데이터파입에 유의해야 한다.
    public cardAdapter(ArrayList<cardItem> myDataset) {
        mDataset = myDataset;
    }


    //뷰홀더
    // Create new views (invoked by the layout manager)
    @Override
    public cardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String a = mDataset.get(position).getThumbnail();
        Picasso.get().load(a).into(holder.thumbnail);

        holder.title.setText(mDataset.get(position).getTitle());
        holder.addr1.setText("주소 :"+mDataset.get(position).getAddr1());
        holder.eventstartdate.setText("축제 시작 :"+mDataset.get(position).getEventstartdate());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}