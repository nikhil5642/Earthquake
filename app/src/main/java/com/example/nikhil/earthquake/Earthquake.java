package com.example.nikhil.earthquake;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

/**
 * Created by nikhil on 5/2/17.
 */

public class Earthquake {
    private String mRange;
    private String mLocation;
    private long mTime;
    private String mUrl;
    public Earthquake(String a,String b,long c,String Url){
        mRange=a;
        mLocation=b;
        mTime=c;
       mUrl=Url;
    }

    public String getmRange(){
    return mRange;
    }
    public String getmLocation(){
        return mLocation;
    }
   protected long getmTime(){return mTime;}
    public String getmUri(){return mUrl;}

    public void setmRange(String mag) {
        mRange=mag;
    }

    public void setmLocation(String place) {
     mLocation=place;
    }

    public void setmDetail(long time) {
        mTime=time;
    }
    public void setmUrl(String Url) {
        mUrl=Url;
    }

}
