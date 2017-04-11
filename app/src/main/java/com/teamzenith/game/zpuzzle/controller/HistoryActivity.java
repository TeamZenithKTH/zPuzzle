package com.teamzenith.game.zpuzzle.controller;


import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import com.teamzenith.game.zpuzzle.R;
import java.util.HashMap;
import com.teamzenith.game.zpuzzle.dbhandler.GetUserHistory;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;


/**
 * Created by Hichem Memmi on 2017-04-05.
 */

/**
 * this class is the history controller
 */
public class HistoryActivity extends AppCompatActivity implements GetUserHistory {
    private HistoryController historyController;
    private User player;
    private String userID;

    private HashMap<Integer, UserHistoryEntry> userHistoryEntry;

    private ListView listHistory;

    /**
     * this method called once the activity is launched
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listHistory = (ListView) findViewById(R.id.listViewHistory);
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

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * get the user history
     * @param userHistoryEntry
     */
    @Override
    public void get(HashMap<Integer, UserHistoryEntry> userHistoryEntry) {
        this.userHistoryEntry = userHistoryEntry;
        HistoryAdapter historyAdapter = new HistoryAdapter(HistoryActivity.this, userHistoryEntry);
        listHistory.setAdapter(historyAdapter);
    }
}