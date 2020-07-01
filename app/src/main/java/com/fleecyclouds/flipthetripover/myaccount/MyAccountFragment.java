package com.fleecyclouds.flipthetripover.myaccount;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.fleecyclouds.flipthetripover.R;
import com.fleecyclouds.flipthetripover.sb.ImageLoadTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView userProfile;
    private OnFragmentInteractionListener mListener;
    private FirebaseAuth mAuth;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
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
        String name,email,photoUrl;

        View v = inflater.inflate(R.layout.fragment_my_account,container,false);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        if(user !=null){

            name = user.getDisplayName();
            email=user.getEmail();
            photoUrl=user.getPhotoUrl().toString();
            TextView userName =(TextView) v.findViewById(R.id.name);
            TextView userEmail =(TextView) v.findViewById(R.id.email);
            userProfile=(ImageView) v.findViewById(R.id.profile);

            userName.append(name);
            userEmail.append(email);
            sendImageRequest(photoUrl);

        }


        Button btnLogout = (Button)v.findViewById(R.id.logoutButton);
        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseAuth.getInstance().signOut();
                revokeAccess();
            }
        });


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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void sendImageRequest(String a){
        //String url="https://movie-phinf.pstatic.net/20200130_198/15803497537218LnrU_JPEG/movie_image.jpg?type=m665_443_2";
        String url =a;
        ImageLoadTask task = new ImageLoadTask(url,userProfile);
        task.execute();
    }
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    private void revokeAccess() {
        mAuth.getCurrentUser().delete();
    }

}
