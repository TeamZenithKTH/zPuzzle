package com.teamzenith.game.zpuzzle.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.SendInvitation;
import com.teamzenith.game.zpuzzle.model.User;

/**
 * Created by Hichem Memmi on 2017-04-11.
 */

public class AfterInvitationGameActivity extends AppCompatActivity {

    private SendInvitation sendInvitation;
    private User player;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.after_invitation_game);

        Intent mIntent = getIntent();
        sendInvitation = (SendInvitation) mIntent.getSerializableExtra("sendInvitation");
        player = (User) mIntent.getSerializableExtra("player");
        AlertDialog alertDialog = new AlertDialog.Builder(
                AfterInvitationGameActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Your Present Text");

        // Setting Dialog Message
        alertDialog.setMessage(Html.fromHtml("<font color='#EE0000'>Your Present:</font> "+sendInvitation.getPresentText()+".<br> <font color='#EE0000'>From:</font> "+ sendInvitation.getSenderName()));

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.zpuzzle);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(AfterInvitationGameActivity.this, InboxActivity.class);

                intent.putExtra("player",player);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


}
