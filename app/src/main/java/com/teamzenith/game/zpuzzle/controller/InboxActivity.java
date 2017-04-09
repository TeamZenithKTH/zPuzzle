package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        invitationsController = new InvitationsController();
        Intent mIntent = getIntent();
        player = (User) mIntent.getSerializableExtra("player");
        invitationsController.getUserChallenges(InboxActivity.this, player.getUserID());
        listInvitations = (ListView) findViewById(R.id.inbox_list_view);

    }

    @Override
    public void getMyChallenges(HashMap<Integer, SendInvitation> getInvitation) {
        this.getInvitation = getInvitation;
        InboxInvitationsAdapter adapter = new InboxInvitationsAdapter(InboxActivity.this, getInvitation);
        listInvitations.setAdapter(adapter);

    }
}
