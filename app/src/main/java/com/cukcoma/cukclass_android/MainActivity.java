package com.cukcoma.cukclass_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    int btnHeight = 250;
    LinearLayout Btnview;
    ImageButton map;
    String[] arr1 = {"국제","니콜스","비르투스","마리아","다솔"};
    String[] arr2 = {"K","N","V","M","D"};
    String[] arr3 = {"International_hub","Nicols","Virtus","Maria","Dasol"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ibmplexsanskrregular.otf");
        getWindow().setStatusBarColor(ContextCompat.getColor(this ,R.color.black));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView t = (TextView) findViewById(R.id.navigationText);
        set();
        t.setText("건물선택");
        t.setTypeface(typeface);
        makeView(6);
    }
    public void set(){
        Btnview = findViewById(R.id.Buttonline);
        map = findViewById(R.id.imageButton2);
        map.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, zoomImage.class);
                myIntent.putExtra("image", "1");
                startActivity(myIntent);
            }
        });
        Btnview.setBackgroundColor(Color.rgb(229,229,229));
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

    public void makeView(int k) {

        Display display = getWindowManager().getDefaultDisplay();  // in Activity
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int width = size.x;
        int height = size.y;

        int circle = btnHeight-50; //동그라미 버튼 크기(200)

        LinearLayout.LayoutParams Tmargin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        LinearLayout.LayoutParams T2margin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        LinearLayout.LayoutParams Bmargin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams B2margin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams Nmargin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams B3margin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        Nmargin.setMargins(25,30,width-265,0); //원마진
        Tmargin.setMargins(300,10,20,0);
        T2margin.setMargins(300,135,20,0);
        Bmargin.setMargins(20,10,20,0);
        B2margin.setMargins(20,10,20,10);
        //마진값 설정..

        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ibmplexsanskrregular.otf");

        for (int i=1; i<=arr1.length;i++) {
            final int idx = i;

            FrameLayout Contentview = new FrameLayout(this);
            Btnview.addView(Contentview);

            //프레임레이아웃 설정
            Contentview.setBackgroundColor(Color.rgb(255,255,255));
            Contentview.setBackgroundResource(R.drawable.txtradius);
            Contentview.setLayoutParams(Bmargin);
            Contentview.setElevation(5);
            if(i==arr1.length){
                Contentview.setLayoutParams(B2margin);
            }

            //건물이름(한글)뷰
            TextView view = new TextView(this);
            view.setHeight(btnHeight/2);
            view.setTypeface(typeface);
            view.setText(arr1[i - 1] + "관");
            view.setLayoutParams(Tmargin);
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setTextColor(Color.rgb(0, 0, 0));
            view.setTextSize(textSizing());
            view.setGravity(Gravity.LEFT|Gravity.BOTTOM);
            Contentview.addView(view);

            //건물이름(영어)뷰
            TextView view2 = new TextView(this);
            view2.setTypeface(typeface);
            view2.setHeight(btnHeight/2);
            view2.setText(arr3[i - 1]);
            view2.setLayoutParams(T2margin);
            view2.setBackgroundColor(Color.TRANSPARENT);
            view2.setTextColor(Color.rgb(188,188,188));
            view2.setTextSize(textSizing());
            view2.setGravity(Gravity.LEFT | Gravity.TOP);
            Contentview.addView(view2);

            //숫자뷰
            TextView Numview = new TextView(this);
            Numview.setHeight(circle);
//            Numview.setTypeface(typeface);
            Numview.setText(arr2[i - 1]);
            Numview.setLayoutParams(Nmargin);
            Numview.setBackgroundResource(R.drawable.btnradius);
            Numview.setTextSize(45);
            //Numview.setTypeface(null, Typeface.BOLD);
            Numview.setTextColor(Color.rgb(0, 0, 0));
            Numview.setGravity(Gravity.CENTER);
            //Numview.setElevation(5);
            Contentview.addView(Numview);


            //버튼은 수평정렬
            Button btn = new Button(this);
            btn.setHeight(btnHeight);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setBackgroundResource(R.drawable.ripple);
            //TRANSPARENT : 투명
            btn.setLayoutParams(B3margin);
            btn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextIntent = new Intent(MainActivity.this, selectfloor.class);
                    nextIntent.putExtra("buildingname", idx - 1);
                    startActivity(nextIntent);
                }
            });
            Contentview.addView(btn);
        }
    }
}