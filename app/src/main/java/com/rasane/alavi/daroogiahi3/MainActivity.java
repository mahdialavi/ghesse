package com.rasane.alavi.daroogiahi3;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends ActivityEnhanced{
    RecyclerView rvadvie;
    LinearLayoutManager manager;
    AdvieAdapter advieAdapter;
    private boolean exit = false;
    SharedPreferences setting;
    public static String iddone="";
    public static String name = "";
    public static String name2 = "";
    public static String muUrl= "";
    MediaPlayer mediaPlayer;
    ImageView playmain;
    ImageView hambergurmenu;

    private static boolean cancel=false ;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    private static String imanurl= "http://dl.nex1music.ir/1397/01/15/Iman%20Karimi%20-%20Nafasgir%20[128].mp3";
//    private static String locallink= "https://www.androidtutorialpoint.com/wp-content/uploads/2016/09/AndroidDownloadManager.mp3";
    private static String locallink= "http://192.168.244.2/ghesse/hoco.mp3";
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        playmain = (ImageView) findViewById(R.id.playmain);
        advieAdapter  = new AdvieAdapter(SplashActivity.advieArraylist, this);
        manager = new LinearLayoutManager(G.context);
        rvadvie = (RecyclerView) findViewById(R.id.AdvieRecycle);

        mediaPlayer = MediaPlayer.create(this, R.raw.ahang);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        rvadvie.setLayoutManager(manager);
        rvadvie.setHasFixedSize(true);
        rvadvie.setAdapter(advieAdapter);
        findViewById(R.id.txtexit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

//        findViewById(R.id.sharebtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);
//            }
//        });
        advieAdapter.setonClickedRecycleListener(new DeleteNotification() {
            @Override
            public void onActionclicked(String position) {

            }
     @Override
            public void onActionclicked(long id) {
         dialogConfirmCheckout();
//         Toast.makeText(G.context,muUrl,Toast.LENGTH_SHORT).show();
         }
        });
        playmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    playmain.setImageResource(R.drawable.volume);
                    mediaPlayer.start();
                } else {
                    playmain.setImageResource(R.drawable.mute);
                    mediaPlayer.pause();
                }
            }
        });
        hambergurmenu= (ImageView) findViewById(R.id.hambergurmenu);
        hambergurmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
    }
    public void dialogConfirmCheckout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.confirmation));
        builder.setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                new DownloadFileFromURL().execute(muUrl);


            }
        });
        builder.setNegativeButton(R.string.NO, null);
        builder.show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("در حال دانلود داستان .لطفا صبر کنید...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.setIndeterminate(false);
                pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(G.context,name,Toast.LENGTH_SHORT).show();
                        delete(name);
                        cancel=true;
                    }
                });
                pDialog.show();
                return pDialog;
            default:
                return null; }
    }

  class DownloadFileFromURL extends AsyncTask<String, String, String> {
        //  DownloadFileFromURL.Status
        protected void onPreExecute() {
            super.onPreExecute();

            showDialog(progress_bar_type);
        }
        @Override
        protected String doInBackground(String... url_music) {
            int count;
            try {
                URL url = new URL(url_music[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                // Output stream
                @SuppressLint("SdCardPath") OutputStream output = new FileOutputStream("/sdcard/" +name);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1 && !cancel) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);

            if (!cancel) {
                Intent intent = new Intent(G.context, ActivityShow.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", name);
                intent.putExtra("name2", name2);
                G.context.startActivity(intent);

            } else {
                delete(name);
                cancel=false;

//                Toast.makeText(MainActivity.this, "دانلود داستان کنسل شد!", Toast.LENGTH_SHORT).show();


            }

        }
    }
    public static boolean delete(String name) {
        File extStore = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + name );
        if (extStore.exists()) {
            extStore.delete();
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        mediaPlayer.start();
        playmain.setImageResource(R.drawable.volume);
        SplashActivity.advieArraylist.clear();
        SplashActivity.selectAdvie();
        advieAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        if (exit) {
            System.exit(0);
        } else {
            exit = true;
            Toast.makeText(G.context, R.string.confirm_exit,Toast.LENGTH_SHORT).show();
            G.hanler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }
    }




