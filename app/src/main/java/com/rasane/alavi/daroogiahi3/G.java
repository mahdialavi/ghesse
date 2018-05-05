package com.rasane.alavi.daroogiahi3;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by alavi on 2/11/2018.
 */

public class G extends Application {

    public static final Handler hanler = new Handler();
    public static Context context;
    public static AppCompatActivity ACTIVITY;
//    public static String Webservice="http://192.168.1.101";
    public static String Webservice="http://hendiabootik.com";
    public static String direction = Environment.getExternalStorageDirectory().getAbsolutePath() + "/herbal_meds";
    public static void startActivity(Class targetActivity) {
        startActivity(targetActivity, false);
    }
//    private static final String FIRST_LAUNCH = "_.FIRST_LAUNCH";
//    private SharedPreferences sharedPreferences;
//    private SharedPreferences prefs;

//    public G(Context context) {
//        this.context = context;
//        sharedPreferences = context.getSharedPreferences("MAIN_PREF", Context.MODE_PRIVATE);
//        prefs = PreferenceManager.getDefaultSharedPreferences(context);
//    }

//    public void setFirstLaunch(boolean flag) {
//        sharedPreferences.edit().putBoolean(FIRST_LAUNCH, flag).apply();
//    }
//
//    public boolean isFirstLaunch() {
//        return sharedPreferences.getBoolean(FIRST_LAUNCH, true);
//    }

    public static void startActivity(Class targetActivity, boolean finish) {
        Intent intent = new Intent(ACTIVITY, targetActivity);
        ACTIVITY.startActivity(intent);
        if (finish) {
            ACTIVITY.finish();
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        File file = new File(direction);
        file.mkdirs();
        try {
                file.createNewFile();
                copyFromAssets(getBaseContext().getAssets().open("material_book.sqlite"),
                        new FileOutputStream(direction + "/material_book.sqlite"));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void copyFromAssets(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);

        }
        inputStream.close();
        outputStream.close();

    }
}