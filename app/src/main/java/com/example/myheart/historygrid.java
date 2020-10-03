package com.example.myheart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class historygrid extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<historydata> hisortylist;
    private String mail;
    public historygrid(Context context, int layout, ArrayList<historydata> hisortylist) {
        this.context = context;
        this.layout = layout;
        this.hisortylist = hisortylist;
    }



    @Override
    public int getCount() {
        return hisortylist.size();
    }

    @Override
    public Object getItem(int position) {
        return hisortylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        TextView bpm,date,time,casee;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        ViewHolder holder=new ViewHolder();

        if(row == null)
        {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.bpm=(TextView)row.findViewById(R.id.historybpm);
            holder.date=(TextView)row.findViewById(R.id.historydate);
            holder.time=(TextView)row.findViewById(R.id.historytime);
            holder.casee=(TextView)row.findViewById(R.id.historycase);
            row.setTag(holder);
        }
        else {
            holder=(ViewHolder)row.getTag();
        }
        historydata pro=hisortylist.get(position);
        holder.bpm.setText(pro.getBpm());
        holder.date.setText(pro.getDate());
        holder.time.setText(pro.getTime());
        holder.casee.setText(pro.getCasee());


        return row;
    }
}
