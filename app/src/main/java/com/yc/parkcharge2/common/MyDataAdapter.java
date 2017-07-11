package com.yc.parkcharge2.common;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;


import com.yc.parkcharge2.R;

import java.util.List;
import java.util.Map;


/**
 * Created by a on 2016/6/20.
 */
public class MyDataAdapter extends SimpleAdapter {

    private Context context;

    public MyDataAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= super.getView(position, convertView, parent);
        if(position%2==0){
            view.setBackgroundColor(Color.parseColor(context.getString(R.string.row_even)));
        }else{
            view.setBackgroundColor((Color.parseColor(context.getString(R.string.row_odd))));
        }
        return view;
    }

    public Context getContext() {
        return context;
    }
}
