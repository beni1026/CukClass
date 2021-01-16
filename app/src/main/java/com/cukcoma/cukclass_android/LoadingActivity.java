package com.cukcoma.cukclass_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getWindow().setStatusBarColor(ContextCompat.getColor(this ,R.color.black));


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }


}