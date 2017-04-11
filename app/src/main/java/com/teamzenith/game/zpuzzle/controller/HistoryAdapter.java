package com.teamzenith.game.zpuzzle.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.util.HashMap;


/**
 * Created by Shubha on 4/7/2017.
 */

    public class HistoryAdapter extends BaseAdapter{

        private HashMap<Integer, UserHistoryEntry> userHistory = new HashMap<>();
        private Activity historyActivity;
        private static LayoutInflater inflater=null;

        public HistoryAdapter(Activity historyActivity, HashMap<Integer, UserHistoryEntry> userHistory) {
            this.historyActivity=historyActivity;
            this.userHistory=userHistory;
            inflater = (LayoutInflater)historyActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return userHistory.size();
        }

        @Override
        public Object getItem(int position) {
            return userHistory.get(position);
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

            //TextView date = (TextView)view.findViewById(R.id.date);
            TextView level = (TextView)view.findViewById(R.id.history_level);
            TextView numOfMoves = (TextView)view.findViewById(R.id.history_numOfMoves);
            TextView time = (TextView)view.findViewById(R.id.history_timeTaken);
            ImageView thumb_image=(ImageView) view.findViewById(R.id.history_puzzleImage);

            level.setText(this.userHistory.get(position).getLevel().toString());
            numOfMoves.setText(this.userHistory.get(position).getCountMovementString());
            time.setText(this.userHistory.get(position).getTimerCounterString());
            Picasso.with(context).load(userHistory.get(position).getImageFile()).into(thumb_image);
            return view;
        }
    }

