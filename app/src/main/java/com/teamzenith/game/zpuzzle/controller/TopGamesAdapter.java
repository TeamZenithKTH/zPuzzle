package com.teamzenith.game.zpuzzle.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.util.HashMap;

/**
 * Created by memmi on 2017-04-07.
 */




public class TopGamesAdapter extends BaseAdapter {

    private HashMap<Integer, UserHistoryEntry> topGames = new HashMap<>();
    private Activity TopGamesActivity;
    private static LayoutInflater inflater=null;


    public TopGamesAdapter(Activity TopGamesActivity, HashMap<Integer, UserHistoryEntry> topGames) {
        this.TopGamesActivity=TopGamesActivity;
        this.topGames=topGames;
        inflater = (LayoutInflater)TopGamesActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return topGames.size();
    }

    @Override
    public Object getItem(int position) {
        return topGames.get(position);
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
            view = inflater.inflate(R.layout.top_games_item, null);

        //TextView date = (TextView)view.findViewById(R.id.date);
        TextView level = (TextView)view.findViewById(R.id.top_games_level);
        TextView numOfMoves = (TextView)view.findViewById(R.id.top_games_numOfMoves);
        TextView time = (TextView)view.findViewById(R.id.top_games_timeTaken);
        ImageView thumb_image=(ImageView) view.findViewById(R.id.top_games_puzzleImage);

        level.setText(this.topGames.get(position).getLevel().toString());
        numOfMoves.setText(this.topGames.get(position).getCountMovementString());
        time.setText(this.topGames.get(position).getTimerCounterString());
        Picasso.with(context).load(topGames.get(position).getImageFile()).into(thumb_image);
        return view;
    }
}
