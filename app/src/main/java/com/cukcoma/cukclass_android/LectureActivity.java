package com.cukcoma.cukclass_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class LectureActivity extends AppCompatActivity {

    int buildingIdx = 0, btnHeight = 250;
    String[] ArrayBuildingName = {"K", "N", "V", "M", "D"};
    String buildingFloor = "1";
    String bnf = "N1";
    String urlI = "1/1";
    ArrayList<Button> btns = new ArrayList<>();
    LinearLayout BottomLinear;
    TextView title;
    ImageView Image;
    Button imageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this ,R.color.black));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        set();
        new urlService().execute();
    }

    public int textSizing(){
        Display display = getWindowManager().getDefaultDisplay();  // in Activity
        Point smt = new Point();
        display.getRealSize(smt); // or getSize(size)
        int size = smt.x;
        if(size <= 500){
            size = 13;
        }
        else if(size <= 900){
            size = 15;
        }
        else if(size <= 1200){
            size = 16;
        }
        else if(size <= 1600){
            size = 19;
        }
        else{
            size = 22;
        }
        return size;
    }

    public void set() {
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ibmplexsanskrregular.otf");
        BottomLinear = findViewById(R.id.bottomview);
        BottomLinear.setBackgroundColor(Color.rgb(229, 229, 229));
        title = findViewById(R.id.navigationText);
        Intent get = getIntent();
        final int num = get.getIntExtra("buildingname",0);
        final int floor = get.getIntExtra("floor",1);
        final String key = get.getStringExtra("key");
        buildingFloor = String.valueOf(floor);
        bnf = ArrayBuildingName[num] + buildingFloor;
        urlI = key + "/" + buildingFloor;
        title.setText(bnf);
        title.setTypeface(typeface);
        Image = findViewById(R.id.buildingImage);
        imageBtn = findViewById(R.id.imgButton);
        for(int i = 0; i <= 100; i++){
            btns.add(new Button(this));
        }
    }

    public String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }

    private class urlService extends AsyncTask<Void,Void,Void>{

        String save = "";
        JSONObject classObj;
        int btnHeight = 250;
        Bitmap bmImg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... String){
            getClassname("http://3.34.84.147/class/" + bnf);
            getClassImage("http://3.34.84.147/floor/image/" + urlI);
            return null;
        }

        protected void getClassImage(String requestUrl){
            try{
                Log.d("Log", requestUrl);
                URL url = new URL(requestUrl);
                HttpURLConnection a = (HttpURLConnection) url.openConnection();
                InputStreamReader is = new InputStreamReader(a.getInputStream(),"UTF-8");
                BufferedReader bf = new BufferedReader(is);
                String temp = "";
                String save = "";
                JSONObject imageJson = null;
                while((temp = bf.readLine()) != null){
                    save = save + temp;
                }
                imageJson = new JSONObject(save);
                JSONArray ImageArray = (JSONArray) imageJson.get("data");
                JSONObject _image = (JSONObject) ImageArray.get(0);
                String imageUrl = (String) _image.get("image");
                URL _imageUrl = new URL(imageUrl);
                a = (HttpURLConnection) _imageUrl.openConnection();
                a.setDoInput(true);
                a.connect();
                int length = a.getContentLength();
                InputStream Imgis = a.getInputStream();
                bmImg = BitmapFactory.decodeStream(Imgis);
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        protected void getClassname(String requestUrl){
            try {
                URL url = new URL(requestUrl);
                HttpURLConnection a = (HttpURLConnection) url.openConnection();
                InputStreamReader is = new InputStreamReader(a.getInputStream(),"UTF-8");
                BufferedReader bf = new BufferedReader(is);
                String temp = "";
                while((temp = bf.readLine()) != null){
                    save = save + temp;
                }
                classObj = new JSONObject(save);
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                JSONArray go = (JSONArray) classObj.get("data");
                setImg();
                makeBtn(go);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }

        protected void setImg(){
            bmImg = Bitmap.createScaledBitmap(bmImg, Image.getWidth() - 20, Image.getHeight() - 20, true);
            Image.setImageBitmap(bmImg);
            Image.setElevation(5);
            Image.setBackgroundResource(R.drawable.buttonradius);
            Image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageBtn.setBackgroundResource(R.drawable.ripple);
            imageBtn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextIntent = new Intent(LectureActivity.this, zoomImage.class);
                    nextIntent.putExtra("image",BitmapToString(bmImg));
                    startActivity(nextIntent);
                }
            });
        }

        protected void makeBtn(JSONArray className) {
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            margin.setMargins(20,10,20,0);

            LinearLayout.LayoutParams lastMargin = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            lastMargin.setMargins(20,10,20,20);
            Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ibmplexsanskrregular.otf");
            for(int i = 0; i < className.length(); i++){
                String btnName = "";
                try {
                    btnName = className.get(i).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                btns.get(i).setText(btnName);
                btns.get(i).setTextSize(textSizing());
                btns.get(i).setTypeface(typeface);
                btns.get(i).setBackgroundResource(R.drawable.ripplelecture);
                btns.get(i).setHeight(btnHeight);
                BottomLinear.addView(btns.get(i));
                if(i == className.length() - 1){
                    btns.get(i).setLayoutParams(lastMargin);
                }
                else{
                    btns.get(i).setLayoutParams(margin);
                }
                final String pushClassName = btnName;
                btns.get(i).setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(LectureActivity.this, TimeTable.class);
                        myIntent.putExtra("className", pushClassName);
                        startActivity(myIntent);
                    }
                });
            }
        }
    }

}
