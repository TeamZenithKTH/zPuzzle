package com.teamzenith.game.zpuzzle.controller;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.dbhandler.GetUserInvitations;
import com.teamzenith.game.zpuzzle.model.SendInvitation;
import com.teamzenith.game.zpuzzle.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SentInvitationActivity extends ListActivity implements GetUserInvitations {
    //private ArrayList<String> list = new ArrayList<String>();
    private HashMap<Integer, SendInvitation> usersList = new HashMap<>();
    //private ArrayAdapter<String> adapter;
    private InvitationsController invitationsController;
    private User player;
    private SendInvitation sendInvitation;
    private ListView itemsList;
    private List<SendInvitation> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_invitation);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        itemsList = (ListView) findViewById(android.R.id.list);
        invitationsController = new InvitationsController();
        Intent mIntent = getIntent();
        player = (User) mIntent.getSerializableExtra("player");
        list = new ArrayList<>();
        invitationsController.getUserInvitaionsHistory(SentInvitationActivity.this, player.getUserID());
    }

    @Override
    public void get(SendInvitation sendInvitation) {
        this.sendInvitation = sendInvitation;
        list.add(sendInvitation);
        for (int index = 0; index < list.size(); index++) {
            usersList.put(index, list.get(index));
        }
    }
}
