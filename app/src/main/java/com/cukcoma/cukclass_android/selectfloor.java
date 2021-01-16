package com.cukcoma.cukclass_android;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.lang.reflect.Array;
import java.util.*;
import androidx.core.content.ContextCompat;
import android.content.Intent;
public class selectfloor extends AppCompatActivity {

    int margin = 5;
    int btnHeight = 250;
    LinearLayout bottomview;
    TextView title;
    ArrayList<Button> btnArray = new ArrayList();
    String[] ArrayBuildingName = {"국제관","니콜스","비르투스","마리아","다솔"};
    String[] ArrayImage = {"i_hub", "nicols", "virtus","maria","dasol"};
    String[] buildingKey = {"1","4","6","3","7"};
    int Jnum;
    int[] floorNum = {3, 4, 4, 4, 7};
    ImageView Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectfloor);
        getWindow().setStatusBarColor(ContextCompat.getColor(this ,R.color.black));
        set();
        Intent getFloor = getIntent();
        final int num = getFloor.getIntExtra("buildingname",0);
        Jnum = num;
        System.out.println(num);
        setTitle(ArrayBuildingName[num]);
        setImage(num);
        makeBtn(floorNum[num]);
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

    public void set(){
        bottomview = findViewById(R.id.bottomview);
        bottomview.setBackgroundColor(Color.rgb(229,229,229));
        title = findViewById(R.id.navigationText);
        Image = findViewById(R.id.buildingImage);
    }

    public void setImage(int idx){
        int resource = this.getResources().getIdentifier(ArrayImage[idx],"drawable",this.getPackageName());
        Image.setImageResource(resource);
//        Image.setScaleType(ImageView.ScaleType.);
        Image.setBackgroundResource(R.drawable.buttonradius);
    }

    public void makeBtn(int idx){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int width = size.x;
        int height = size.y;
        LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        margin.setMargins(20,10,20,0);
        LinearLayout.LayoutParams bmargin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        LinearLayout.LayoutParams lastMargin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams Emargin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams Kmargin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        Kmargin.setMargins(20,10,20,0);
        Emargin.setMargins(20,135,20,0);
        lastMargin.setMargins(20,10,20,20);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ibmplexsanskrregular.otf");
        for(int i = 1; i <= idx; i++){
            if(Jnum == 4 && i == 1){
                continue;
            }
            FrameLayout Frame = new FrameLayout(this);
            Frame.setBackgroundColor(Color.rgb(255,255,255));
            if(i == idx) {
                Frame.setLayoutParams(lastMargin);
            }
            else {
                Frame.setLayoutParams(margin);
            }
            Frame.setBackgroundResource(R.drawable.buttonradius);
            Frame.setElevation(5);
            bottomview.addView(Frame);
            TextView K = new TextView(this);
            K.setText(i + "층");
            K.setTextSize(textSizing());
            K.setTextColor(Color.rgb(0,0,0));
            K.setTypeface(typeface);
            K.setHeight(btnHeight/2);
            K.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            K.setBackgroundColor(getResources().getColor(R.color.empty));
            K.setLayoutParams(Kmargin);
            Frame.addView(K);
            TextView E = new TextView(this);
            E.setText(i + "floor");
            E.setTextSize(textSizing());
            E.setTextColor(Color.rgb(188,188,188 ));
            E.setTypeface(typeface);
            E.setHeight(btnHeight/2);
            E.setGravity(Gravity.CENTER | Gravity.TOP);
            E.setBackgroundColor(getResources().getColor(R.color.empty));
            E.setLayoutParams(Emargin);
            Frame.addView(E);

            Button thisbtn = new Button(this);
//            thisbtn.setBackgroundColor(Color.TRANSPARENT);
            thisbtn.setBackgroundResource(R.drawable.ripple);
            thisbtn.setLayoutParams(bmargin);
            thisbtn.setHeight(btnHeight);
            final int finalI = i;
            thisbtn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextIntent = new Intent(selectfloor.this, LectureActivity.class);
                    nextIntent.putExtra("buildingname", Jnum);
                    nextIntent.putExtra("floor", finalI);
                    nextIntent.putExtra("key",buildingKey[Jnum]);
                    startActivity(nextIntent);
                }
            });
            Frame.addView(thisbtn);
        }
    }

    public void setTitle(String str){
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ibmplexsanskrregular.otf");
        title.setText(str);
        title.setTypeface(typeface);
    }
}