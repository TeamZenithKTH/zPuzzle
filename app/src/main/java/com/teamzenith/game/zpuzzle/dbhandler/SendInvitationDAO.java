package com.teamzenith.game.zpuzzle.dbhandler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamzenith.game.zpuzzle.model.SendInvitation;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by alaaalkassar on 4/7/17.
 */

public class SendInvitationDAO {
    private SendInvitation sendInvitation;
    private String userID,friendID;
    private boolean status = false;
    private String date;
    private DatabaseReference mDatabase;
    private DatabaseReference invitationsDatabase, usersDB;
    private GetUserInvitations getUserInvitations;
    public SendInvitationDAO(){

    }
    public void send(SendInvitation sendInvitation, String userID) throws ParseException {
        this.sendInvitation = sendInvitation;
        this.userID = userID;
        this.date = date();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = mDatabase.child("Invitations").child(userID).child(date);
        usersRef.setValue(sendInvitation);
    }
    public void sendToFriend(SendInvitation sendInvitation, String friendID) throws ParseException {
        this.sendInvitation = sendInvitation;
        this.userID = sendInvitation.getUserID();
        this.friendID =friendID;
        sendInvitation.setUserID(friendID);
        this.date = date();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = mDatabase.child("InvitationsFromFriend").child(userID).child(date);
        usersRef.setValue(sendInvitation);
    }

    private String date() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }
    public void getMySentInvitations(String userID) {
        this.userID = userID;
        this.date = date();
        invitationsDatabase = FirebaseDatabase.getInstance().getReference().child("Invitations");
        final DatabaseReference invitationsRef = invitationsDatabase.child(userID);
        invitationsRef .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long num = dataSnapshot.getChildrenCount();
                for (DataSnapshot invitationsSnapshot : dataSnapshot.getChildren()) {
                    SendInvitation userInvitationsEntry = invitationsSnapshot.getValue(SendInvitation.class);
                    getUserInvitations.get(userInvitationsEntry);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setListener(GetUserInvitations getUserInvitations) {
        this.getUserInvitations = getUserInvitations;
    }
}
