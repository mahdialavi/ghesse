package com.rasane.alavi.daroogiahi3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.TooManyListenersException;

/**
 * Created by alavi on 2/11/2018.
 */

public class AdvieAdapter extends RecyclerView.Adapter<AdvieAdapter.HerosViewHolder> {
    private Context context;
    String urls = "";

    private ArrayList<Heros> advieArraylist =new ArrayList<>();
    private ProgressDialog progressDialog;
    private DeleteNotification deletenotification;

    AdvieAdapter(ArrayList<Heros> advieArraylist, MainActivity mainActivity) {
        this.advieArraylist=advieArraylist;
        this.context=mainActivity;
        progressDialog = new ProgressDialog(mainActivity);
    }
    @Override
    public HerosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.heros_recycle_rows,parent,false);
        return new HerosViewHolder(view);
    }
    public void setonClickedRecycleListener(DeleteNotification deleteNotification) {
        this.deletenotification=deleteNotification;
    }
    @Override
    public void onBindViewHolder(final HerosViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Heros heros= advieArraylist.get(position);
        holder.txtName.setText(heros.name);
        final String getUrls=heros.getUrls();
        holder.linearLayout.setId(position);

        MainActivity.iddone=heros.getMuname();
        if (check(heros.getMuname())) {
            holder.imgdownload.setImageResource(R.drawable.downloadde);
//            Toast.makeText(G.context,heros.getMuname(), Toast.LENGTH_LONG).show();
        } else {
            holder.imgdownload.setImageResource(R.drawable.download2);
            }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.name=heros.getMuname();
                MainActivity.muUrl=G.Webservice+heros.getUrls();
                MainActivity.name2=heros.getName();
                if(check(heros.getMuname())){
                    Intent intent = new Intent(G.context, ActivityShow.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("name", heros.getMuname());
                    intent.putExtra("name2", heros.getName());
                    G.context.startActivity(intent);
                }else {
                    if (OnlineCheck.isOnline()) {

                        deletenotification.onActionclicked(position);
                    } else {
                        Toast.makeText(G.context, R.string.connectto,Toast.LENGTH_SHORT).show();
                    }
                    }
                    }
        });
    }
    public boolean check(String name) {
        File extStore = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + name );
        if (extStore.exists()) {
            return true;
        }
        return false;
    }
    @Override
    public int getItemCount() {

        return advieArraylist.size();
    }

    public class HerosViewHolder extends RecyclerView.ViewHolder{

        public ImageView herosImg;
        public ImageView imgdownload;
        LinearLayout linearLayout;
        public TextView txtName;
        public HerosViewHolder(View itemView) {
            super(itemView);

            herosImg=(ImageView)itemView.findViewById(R.id.herosImg);
            txtName=(TextView)itemView.findViewById(R.id.txtHerosName);
            imgdownload = (ImageView) itemView.findViewById(R.id.imgdownload);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linear);
        }
    }
}