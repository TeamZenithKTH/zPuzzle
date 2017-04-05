package com.teamzenith.game.zpuzzle.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.util.ImageConverter;

import java.util.HashMap;

/**
 * Created by memmi on 2017-04-05.
 */

public class HistoryAdapter extends BaseAdapter {


    private static LayoutInflater inflater=null;
    Activity historyActivity;
    HashMap<Integer,String> values;
    public HistoryAdapter(Activity historyActivity, HashMap<Integer,String> values){
        this.historyActivity=historyActivity;
        this.values=values;
        inflater=(LayoutInflater) historyActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view =convertView;
        if(convertView==null)
            view = inflater.inflate(R.layout.history_item, null);

        TextView pos = (TextView)view.findViewById(R.id.pos); // title
        ImageView val = (ImageView)view.findViewById(R.id.val); // artist name

        pos.setText(String.valueOf(position));
        val.setImageDrawable(new ImageConverter().convertToBitmap(values.get(position)));


        return view;

    }
}
