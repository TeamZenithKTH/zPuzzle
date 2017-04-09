package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.dbhandler.GetMyFriendsChallengInvitations;
import com.teamzenith.game.zpuzzle.model.SendInvitation;
import com.teamzenith.game.zpuzzle.model.User;

import java.util.HashMap;

public class InboxActivity extends AppCompatActivity implements GetMyFriendsChallengInvitations {
    private HashMap<Integer, SendInvitation> getInvitation;
    private InvitationsController invitationsController;
    private String userID;
    private User player;
    private ListView listInvitations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.inbox_toolbar);
        toolbar.setTitle("Inbox");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        invitationsController = new InvitationsController();
        Intent mIntent = getIntent();
        player = (User) mIntent.getSerializableExtra("player");
        invitationsController.getUserChallenges(InboxActivity.this, player.getUserID());
        listInvitations = (ListView) findViewById(R.id.inbox_list_view);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();// close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getMyChallenges(HashMap<Integer, SendInvitation> getInvitation) {
        this.getInvitation = getInvitation;
        InboxInvitationsAdapter adapter = new InboxInvitationsAdapter(InboxActivity.this, getInvitation);
        listInvitations.setAdapter(adapter);

    }
}
