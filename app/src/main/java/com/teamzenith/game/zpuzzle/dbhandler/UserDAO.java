package com.teamzenith.game.zpuzzle.dbhandler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


/**
 * Created by alaaalkassar on 3/31/17.
 */

public class UserDAO {
    private DatabaseReference mDatabase;
    private String date;
    private Level level;
    private String userID;
    private String countMovementString;
    private String timerCounterString;
    private UserHistoryEntry userHistoryEntry;
    private User user;
    private String userName;
    private String userEmail;
    private String userImage;

    /**
     * @param userHistoryEntry
     * @throws ParseException
     */
    public void insertOnHistoryEntry(UserHistoryEntry userHistoryEntry) throws ParseException {
        this.userHistoryEntry = userHistoryEntry;
        this.userID = userHistoryEntry.getUserID();
        this.level = userHistoryEntry.getLevel();
        this.countMovementString = userHistoryEntry.getCountMovementString();
        this.timerCounterString = userHistoryEntry.getTimerCounterString();
        date = date();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("History").child("userID: "+userID).child("date: "+date).child("Level: "+level.toString()).child("count: "+countMovementString).setValue("time"+timerCounterString);
        //getUserHistory(userID);
    }

    public void insertNewUser(User user) {
        this.user = user;

        this.userID = user.getUserID();
        this.userName = user.getUserName();
        this.userImage = user.getUserImage();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(userID).child(userName).setValue(userImage);

    }

    private String date() throws ParseException {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }

    private void getUserHistory(String userID) {
        this.userID = userID;
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("History").child(userID);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        userHistory((Map<String, Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void userHistory(Map<String, Object> users) {

        ArrayList<Long> userHistorys = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            userHistorys.add((Long) singleUser.get("phone"));
        }

        System.out.println(userHistorys.toString());
    }
}
