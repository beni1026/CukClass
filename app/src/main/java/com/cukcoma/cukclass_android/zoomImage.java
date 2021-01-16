package com.cukcoma.cukclass_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.chrisbanes.photoview.PhotoView;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.Display;


public class zoomImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this ,R.color.black));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        Intent get = getIntent();
        final String Image = get.getStringExtra("image");
        PhotoView photoView = findViewById(R.id.photoView);
        if(Image.equals("1")){
            photoView.setImageResource(R.drawable.img_all_building);
        }
        else {
            Bitmap imageBit = StringToBitmap(Image);
            photoView.setImageBitmap(imageBit);
        }
    }

    public Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


}