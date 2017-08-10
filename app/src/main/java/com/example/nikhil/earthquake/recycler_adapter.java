package com.example.nikhil.earthquake;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nikhil on 11/3/17.
 */

public class recycler_adapter extends RecyclerView.Adapter<recycler_adapter.ViewHolder> {
    public ArrayList<String> mdatas;


    @Override
    public recycler_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(recycler_adapter.ViewHolder holder, int position) {
final String name=mdatas.get(position);
        holder.txth.setText(mdatas.get(position));
        holder.txtf.setText(mdatas.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txth;
        public TextView txtf;

        public ViewHolder(View itemView) {
            super(itemView);
            txth=(TextView)itemView.findViewById(R.id.detail_textview);
            txtf=(TextView)itemView.findViewById(R.id.location_textview);
        }}

        public void add(int position, String item) {
            mdatas.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(String item) {
            int position = mdatas.indexOf(item);
            mdatas.remove(position);
            notifyItemRemoved(position);
        }
        public recycler_adapter(ArrayList<String> myDataset) {
            mdatas = myDataset;
        }
    }

