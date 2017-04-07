package com.teamzenith.game.zpuzzle.dbhandler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamzenith.game.zpuzzle.model.SendInvitation;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by alaaalkassar on 4/7/17.
 */

public class SendInvitationDAO {
    private SendInvitation sendInvitation;
    private String userID;
    private boolean status=false;
    private String date;
    private DatabaseReference mDatabase;
     public void send(SendInvitation sendInvitation,String userID) throws ParseException {
        this.sendInvitation = sendInvitation;
        this.userID=userID;
        this.date = date();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = mDatabase.child("Invitations").child(userID);
        usersRef.setValue(sendInvitation);
    }

    private String date() throws ParseException {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }

}
