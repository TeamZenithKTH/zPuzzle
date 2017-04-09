package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.dbhandler.GetUserInvitations;
import com.teamzenith.game.zpuzzle.model.SendInvitation;
import com.teamzenith.game.zpuzzle.model.User;

import java.util.HashMap;

public class SentInvitationActivity extends AppCompatActivity implements GetUserInvitations {
    private InvitationsController invitationsController;
    private User player;
    private HashMap<Integer, SendInvitation> sendInvitation;
    private ListView listInvitations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_invitation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sent_toolbar);
        toolbar.setTitle("Sent");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        invitationsController = new InvitationsController();
        Intent mIntent = getIntent();
        player = (User) mIntent.getSerializableExtra("player");

        listInvitations = (ListView) findViewById(R.id.list_view_sent_invitations);
        invitationsController.getUserInvitaionsHistory(SentInvitationActivity.this, player.getUserID());
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
    public void get(HashMap<Integer, SendInvitation> sendInvitation) {
        this.sendInvitation = sendInvitation;
        SentInvitationsAdapter adapter = new SentInvitationsAdapter(SentInvitationActivity.this, sendInvitation);
        listInvitations.setAdapter(adapter);
    }
}
