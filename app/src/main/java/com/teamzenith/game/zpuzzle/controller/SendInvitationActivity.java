package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.dbhandler.SendInvitationDAO;
import com.teamzenith.game.zpuzzle.model.SendInvitation;
import com.teamzenith.game.zpuzzle.model.User;

import java.text.ParseException;

public class SendInvitationActivity extends AppCompatActivity {
    private InvetationsController invetationsController;
    private User player;
    private EditText invitationText,senderPresent;
    private ImageView invitationImage;
    private Spinner levelSpinner, friendSpinner;
    private Button send, cancel;
    private SendInvitation sendInvitation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invitation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.send_toolbar);
        toolbar.setTitle("Send Invitation");
        setSupportActionBar(toolbar);
        invetationsController = new InvetationsController();
        Intent mIntent = getIntent();
        player = (User) mIntent.getSerializableExtra("player");
        send = (Button) findViewById(R.id.send_invitation);
        cancel =(Button) findViewById(R.id.cancel_send_invitation);
        invitationText = (EditText) findViewById(R.id.invitation_text);
        invitationImage = (ImageView) findViewById(R.id.invitation_image);
        levelSpinner = (Spinner) findViewById(R.id.select_level);
        friendSpinner = (Spinner) findViewById(R.id.select_friend);
        senderPresent = (EditText) findViewById(R.id.sender_present);
        sendInvitation = new SendInvitation(invitationText.getText().toString(),null,null,null,senderPresent.getText().toString(),false);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    invetationsController.sendInvitationToUser(sendInvitation,player.getUserID());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
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

}
