package com.rasane.alavi.daroogiahi3;

/**
 * Created by alavi on 2/11/2018.
 */

public class Heros {
    String id;
    public String name;
    public String muname;
    public String urls;



    public Heros(String id, String name, String urls, String muname){
        this.id=id;
        this.name=name;

        this.urls=urls;

        this.muname=muname;

    }

    public String getid(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getUrls(){
        return urls;
    }
    public String getMuname(){
        return muname;
    }
}
