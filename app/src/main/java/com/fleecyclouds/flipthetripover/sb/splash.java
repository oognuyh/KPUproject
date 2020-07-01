package com.fleecyclouds.flipthetripover.sb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.fleecyclouds.flipthetripover.R;

public class splash extends AppCompatActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_splash);
        Handler hd = new Handler();
        hd.postDelayed(new splashhandler() , 3000); // 3초 후에 hd Handler 실행
    }

    private class splashhandler implements Runnable {
        public void run() {
            startActivity(new Intent(getApplication(), LoginActivity.class)); // 로딩이 끝난후 이동할 Activity
            splash.this.finish(); // 로딩페이지 Activity Stack에서 제거
        }
    }

}
