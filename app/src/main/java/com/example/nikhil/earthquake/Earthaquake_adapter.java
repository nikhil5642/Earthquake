package com.example.nikhil.earthquake;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.StringPrepParseException;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by nikhil on 5/2/17.
 */

public class Earthaquake_adapter extends ArrayAdapter<Earthquake> {

    public Earthaquake_adapter(Context context, ArrayList<Earthquake> arrayList) {
        super(context, 0, arrayList);
    }
 @RequiresApi(api = Build.VERSION_CODES.N)
 @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list_item_view=convertView;
       if(list_item_view==null){
           list_item_view = LayoutInflater.from(getContext()).inflate(
                   R.layout.list_item, parent, false);
       }
            Earthquake e=getItem(position);
        TextView t1=(TextView)list_item_view.findViewById(R.id.range_textview);
     GradientDrawable circle=(GradientDrawable)t1.getBackground();
     circle.setColor(getMagnitudeColor(Double.parseDouble(e.getmRange())));
        t1.setText(e.getmRange());

        TextView t2=(TextView)list_item_view.findViewById(R.id.location_textview);
     TextView t5 = (TextView) list_item_view.findViewById(R.id.detail_textview);

     if(e.getmLocation().contains("of")) {
         String[] part1 = e.getmLocation().split("of");

         t2.setText(part1[1]);
         t5.setText(part1[0] + "of");
     }
     else {
         t5.setText("");
         t2.setText(e.getmLocation());
     }



     Date date=new Date(e.getmTime());
        TextView t3=(TextView)list_item_view.findViewById(R.id.time_textview);
        t3.setText(formattime(date));
        TextView t4=(TextView)list_item_view.findViewById(R.id.date_textview);
        t4.setText(formatdate(date));
              return list_item_view;
    }
    public String formatdate(Date object){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("LLL dd, yyyy");
        return simpleDateFormat.format(object);
    }
    public String formattime(Date object){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("h:mm a");
        return simpleDateFormat.format(object);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}