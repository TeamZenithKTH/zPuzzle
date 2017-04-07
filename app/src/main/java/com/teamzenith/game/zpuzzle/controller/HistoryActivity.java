package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.dbhandler.GetUserHistory;
import com.teamzenith.game.zpuzzle.dbhandler.UploadToDatabase;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

public class HistoryActivity extends AppCompatActivity implements GetUserHistory{
    private HistoryController historyController;
    private User player;
    private String userID;
    private UserHistoryEntry userHistoryEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.history_toolbar);
        toolbar.setTitle("History");
        setSupportActionBar(toolbar);
        Intent mIntent= getIntent();
        player = (User) mIntent.getSerializableExtra("player");
        userID = player.getUserID();
        historyController= new HistoryController();
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        historyController.setToControllerHistoryActivity(this,userID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
           finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void get(UserHistoryEntry userHistoryEntry) {
        this.userHistoryEntry = userHistoryEntry;
        System.out.println(" Level: " + userHistoryEntry.getLevel() + " Count: " + userHistoryEntry.getCountMovementString() + " Time: " + userHistoryEntry.getTimerCounterString() + " User image: " + userHistoryEntry.getImageFile());

    }
}
