package com.cukcoma.cukclass_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.service.autofill.FillCallback;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Typeface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TimeTable extends AppCompatActivity {
    LinearLayout tableline;
    ArrayList<timetable> List = new ArrayList();
    String name = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ibmplexsanskrregular.otf");
        getWindow().setStatusBarColor(ContextCompat.getColor(this ,R.color.black));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        set();
        new AsyncTaskClass().execute();
    }
    public void set(){
        tableline = findViewById(R.id.tableview);
        tableline.setBackgroundColor(Color.rgb(229,229,229));

    }

    public void navigation(){
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ibmplexsanskrregular.otf");
        TextView t1 = (TextView) findViewById(R.id.navigationText);
        t1.setText("시간표 - "+name);
        t1.setTypeface(typeface);
    }

    class timetable {
        int startTime = 0;
        int endTime = 0;
        String day = "";
        String name = "";

        public timetable(Object startTime, Object endTime, Object day, Object name) {
            this.startTime = Integer.parseInt(startTime.toString());
            this.endTime = Integer.parseInt(endTime.toString());
            this.day = day.toString();
            this.name = name.toString();
        }


    }

    class AsyncTaskClass extends AsyncTask<Void, Void,Void>{
        String save = "";
        JSONObject classObj;
        protected void onPreExcute(){
            super.onPreExecute();

        }
        protected Void doInBackground(Void...Void) {
            Intent gClass = getIntent();
            final String className = gClass.getStringExtra("className");
            name = className;
            Log.d("Log100", "" + className);
            getClass("http://3.34.84.147/timetable/"+className);
            return null;
        }
        protected void getClass(String requestUrl){
            try {
                URL url = new URL(requestUrl);
                HttpURLConnection a = (HttpURLConnection) url.openConnection();
                InputStreamReader isr = new InputStreamReader(a.getInputStream(), "UTF-8");
                BufferedReader bf = new BufferedReader(isr);
                String temp = "";
                while ((temp=bf.readLine())!=null){
                    save = save + temp;

                }
                Log.d("Log3", "" + save);
                classObj = new JSONObject(save); //save를 json에 넣음
                Log.d("Log4", "" + classObj);
                JSONArray Obj = (JSONArray) classObj.get("data");
                Log.d("Log5", "" + Obj.length());
                for (int i = 0; i < Obj.length(); i++) {

                    JSONObject data = (JSONObject) Obj.get(i);
                    Log.d("Log2", "" + data);
                    timetable T = new timetable(data.get("startTime"), data.get("endTime"), data.get("day"), data.get("name"));

                    List.add(T);
                    //System.out.println(List.get(i).name);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            navigation();
            makeView();
            super.onPostExecute(aVoid);

        }

    }

    public void makeView()  {
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/ibmplexsanskrregular.otf");
        //강의실 이름 텍스트뷰
 /*       LinearLayout.LayoutParams textmargin = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        textmargin.setMargins(20,0,20,0);

        TextView t2 = findViewById(R.id.timetabletxt);
        t2.setHeight(150);
        t2.setBackgroundColor(Color.rgb(255,255,255));
        t2.setText(name);
        t2.setTextSize(30);
        t2.setTextColor(Color.rgb(0,0,0));
        t2.setGravity(Gravity.CENTER_VERTICAL);
        t2.setLayoutParams(textmargin);
*/
        //핸드폰 가로,세로길이 추출
        Display display = getWindowManager().getDefaultDisplay();  // in Activity
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int width = size.x;
        //int height = size.y;




        String[] arr1= {"","월","화","수","목","금"};
        String[] arr2= {"","9","10","11","12","1","2","3","4","5","6","7"};



        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        TableLayout table = new TableLayout(this); //TABLE 레이아웃 생성
        table.setBackgroundColor(Color.rgb(229,229,229));
        //table.setElevation(10);
        TableRow row[] = new TableRow[12];
        TextView text[][] = new TextView[12][6]; // 텍스트뷰 2차원 배열로 선언
        for (int tr = 0; tr < 12; tr++) {
            row[tr] = new TableRow(this);
            for (int td = 0; td < 6; td++) {
                text[tr][td] = new TextView(this);
                text[tr][td].setTextSize(25);
                text[tr][td].setHeight(150);
                text[tr][td].setWidth((width-40)/ 6);
                text[tr][td].setTextColor(Color.BLACK);
                text[tr][td].setGravity(Gravity.CENTER);
                text[tr][td].setBackgroundResource(R.drawable.tablebtn);
                text[tr][td].setTypeface(typeface);
                // 월~금 , 9~6교시 글자
                if (tr == 0) {
                    text[0][td].setText(arr1[td]);
                    text[0][td].setBackgroundResource(R.drawable.tablebtnwhite);
                } else {
                    text[tr][0].setText(arr2[tr]);
                    text[tr][0].setBackgroundResource(R.drawable.tablebtnwhite);
                }
                row[tr].addView(text[tr][td]);
                // 데이터 넣기
            }
            table.addView(row[tr],rowLayout);
        }

        for (int i=0;i<List.size();i++) {
            Log.d("Log10", "" +List.get(i).startTime );
            Log.d("Log11", "" +List.get(i).endTime );
            int endT =  List.get(i).endTime;
            if(List.get(i).endTime==-1){
                endT = List.get(i).startTime;
            }
            if (List.get(i).day.equals("월")) {
                for (int k = List.get(i).startTime; k <= endT; k++) {
                    text[k][1].setBackgroundResource(R.drawable.tablebtnred);
                }
            } else if (List.get(i).day.equals("화")) {
                for (int k = List.get(i).startTime; k <= endT; k++) {
                    text[k][2].setBackgroundResource(R.drawable.tablebtnred);
                }
            } else if (List.get(i).day.equals("수")) {
                for (int k = List.get(i).startTime; k <= endT; k++) {
                    text[k][3].setBackgroundResource(R.drawable.tablebtnred);
                }
            } else if (List.get(i).day.equals("목")) {
                for (int k = List.get(i).startTime; k <= endT; k++) {
                    text[k][4].setBackgroundResource(R.drawable.tablebtnred);
                }
            } else {
                for (int k = List.get(i).startTime; k <= endT; k++) {
                    text[k][5].setBackgroundResource(R.drawable.tablebtnred);
                }

            }
        }


        tableline.addView(table);





    }


}