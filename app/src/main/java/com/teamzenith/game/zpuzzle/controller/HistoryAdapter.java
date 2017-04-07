package com.teamzenith.game.zpuzzle.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;
import com.teamzenith.game.zpuzzle.util.ImageConverter;

import java.util.HashMap;

/**
 * Created by memmi on 2017-04-05.
 */

public class HistoryAdapter extends BaseAdapter {


    private static LayoutInflater inflater=null;
    Activity historyActivity;
    HashMap<Integer,UserHistoryEntry> values;
    public HistoryAdapter(Activity historyActivity, HashMap<Integer, UserHistoryEntry> values){
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
        Context context = parent.getContext();
        if(convertView==null)
            view = inflater.inflate(R.layout.history_item, null);

        TextView time = (TextView)view.findViewById(R.id.movement);
        TextView movement = (TextView)view.findViewById(R.id.timercount);
        TextView level = (TextView)view.findViewById(R.id.level);
        ImageView image = (ImageView)view.findViewById(R.id.imageHistoryGame);

        time.setText(values.get(position).getTimerCounterString());
        movement.setText(values.get(position).getCountMovementString());
        level.setText(values.get(position).getLevel());

        Picasso.with(context).load(values.get(position).getImageFile()).into(image);


        return view;

    }
}
