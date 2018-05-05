package com.rasane.alavi.daroogiahi3;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class SplashActivity extends ActivityEnhanced {
    Button button;
    public static SQLiteDatabase database;
    public  static ArrayList<Heros> advieArraylist =new ArrayList<>();
    ProgressDialog progressDoalog;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

        selectAdvie();

        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    G.startActivity(MainActivity.class,true);
                                    }
            },1000);

    }
            @Override
            public void onBackPressed () {
                Toast.makeText(G.context, "لطفا صبر کنید!", Toast.LENGTH_LONG).show();
            }
    public static void selectAdvie(){
        database= SQLiteDatabase.openOrCreateDatabase(G.direction +"/material_book.sqlite",null);
        Cursor cursor=database.rawQuery("SELECT * FROM tbl_heros",null);

        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String urls=cursor.getString(cursor.getColumnIndex("urls"));
            String muname=cursor.getString(cursor.getColumnIndex("muname"));
            Heros heros=new Heros(id,name,urls,muname);
            advieArraylist.add(heros);
        }
    }
            }

