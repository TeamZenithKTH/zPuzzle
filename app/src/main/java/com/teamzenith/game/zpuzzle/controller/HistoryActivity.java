package com.teamzenith.game.zpuzzle.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.util.ImageConverter;

import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {

    private ListView listHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listHistory = (ListView) findViewById(R.id.listViewHistory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.history_toolbar);
        toolbar.setTitle("History");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        HashMap<Integer, String> values = new HashMap<>();


        Bitmap hichem =  BitmapFactory.decodeResource(getResources(), R.drawable.son_goku);
        String hichem_string=new ImageConverter().convertToString(hichem);
        values.put(0,hichem_string);

        Bitmap ala =  BitmapFactory.decodeResource(getResources(), R.drawable.naruto);
        String ala_string=new ImageConverter().convertToString(ala);
        values.put(1,ala_string);

        Bitmap bassam =  BitmapFactory.decodeResource(getResources(), R.drawable.stockholm);
        String bassam_string=new ImageConverter().convertToString(bassam);
        values.put(2,bassam_string);

        HistoryAdapter historyAdapter = new HistoryAdapter(HistoryActivity.this, values);
        listHistory.setAdapter(historyAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
