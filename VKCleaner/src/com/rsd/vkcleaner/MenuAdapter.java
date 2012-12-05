package com.rsd.vkcleaner;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<MenuItem>{

    Context context; 
    int layoutResourceId;    
    ArrayList<MenuItem> data;
    private static LayoutInflater inflater=null;
    
    public MenuAdapter(Context context, int layoutResourceId, ArrayList<MenuItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater)((Activity)context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MenuHolder holder = null;
        
        if(row == null)
        {
            row = inflater.inflate(layoutResourceId, null);
            
            holder = new MenuHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            
            row.setTag(holder);
        }
        
        holder = (MenuHolder)row.getTag();

        MenuItem weather = data.get(position);
        holder.txtTitle.setText(weather.title);
        holder.imgIcon.setImageResource(weather.icon);
        
        return row;
    }
    
    static class MenuHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}