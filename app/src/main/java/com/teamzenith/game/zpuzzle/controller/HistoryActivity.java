package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.dbhandler.GetUserHistory;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity implements GetUserHistory {
    private HistoryController historyController;
    private User player;
    private String userID;
    private HashMap<Integer, UserHistoryEntry> userHistoryEntry;
    private ListView listHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.history_toolbar);
        toolbar.setTitle("History");
        setSupportActionBar(toolbar);
        Intent mIntent = getIntent();
        player = (User) mIntent.getSerializableExtra("player");
        userID = player.getUserID();
        listHistory = (ListView) findViewById(R.id.listViewHistory);
        historyController = new HistoryController();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        historyController.setToControllerHistoryActivity(this, userID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void get(HashMap<Integer, UserHistoryEntry> userHistoryEntry) {
        this.userHistoryEntry = userHistoryEntry;
        HistoryAdapter historyAdapter = new HistoryAdapter(HistoryActivity.this, userHistoryEntry);
        listHistory.setAdapter(historyAdapter);
    }
}