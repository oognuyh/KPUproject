package com.fleecyclouds.flipthetripover.sb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.net.URL;
import java.util.HashMap;

//이미지 불러오는 어뎁터
public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
    private String urlstr;
    private ImageView iv;


    //url 과 비트맵을 매핑해둠
    //메모리 클리어를 위해 함
    private static HashMap<String, Bitmap> bitmapHash =new HashMap<String, Bitmap>();

    public ImageLoadTask(String url, ImageView iv){
        this.iv=iv;
        this.urlstr=url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        iv.setImageBitmap(bitmap);
        iv.invalidate();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap=null;
        try{
            //기존 것들을 제거함
            if(bitmapHash.containsKey(urlstr)){
                Bitmap oldBitmap = bitmapHash.remove(urlstr);
                if(oldBitmap!=null){
                    oldBitmap.recycle();
                    oldBitmap=null;
                }
            }
            URL url = new URL(urlstr);
            bitmap= BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        catch(Exception e){e.printStackTrace();}
        return bitmap;
    }
}