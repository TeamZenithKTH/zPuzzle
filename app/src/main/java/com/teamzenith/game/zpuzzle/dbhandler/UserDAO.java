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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by alaaalkassar on 3/31/17.
 */

public class UserDAO {
    private DatabaseReference mDatabase;
    private DatabaseReference historyDatabase;
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
    private String imageFile;

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
        this.imageFile = userHistoryEntry.getImageFile();
        date = date();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = mDatabase.child("History").child(userID).child(date);
        Map<String, String> userHistory = new HashMap<String, String>();
        userHistory.put("Date", date);
        userHistory.put("Level", level.toString());
        userHistory.put("Count", countMovementString);
        userHistory.put("Time", timerCounterString);
        userHistory.put("Image",imageFile);
        usersRef.setValue(userHistory);
        getUserHistory(userID);

    }

    public void insertNewUser(User user) {
        this.user = user;

        this.userID = user.getUserID();
        this.userName = user.getUserName();
        this.userImage = user.getUserImage();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = mDatabase.child("Users").child(userID);
        Map<String, String> uerMap = new HashMap<String, String>();
        uerMap.put("UserName", userName);
        uerMap.put("UserImage", userImage);
        usersRef.setValue(uerMap);
    }

    private String date() throws ParseException {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }

    public Map<String, String> getUserHistory(String userID) {

        historyDatabase = FirebaseDatabase.getInstance().getReference().child("History");
        final DatabaseReference historyRef = historyDatabase.child(userID);
        final Map<String, String> userHistory = new HashMap<String, String>();
        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long num = dataSnapshot.getChildrenCount();
                System.out.println("Children Number: " + num);
                for (DataSnapshot userHistorySnapshot : dataSnapshot.getChildren()) {
                    String level = (String) userHistorySnapshot.child("Level").getValue();
                    String count = (String) userHistorySnapshot.child("Count").getValue();
                    String time = (String) userHistorySnapshot.child("Time").getValue();
                    String date = (String) userHistorySnapshot.child("Date").getValue();
                    String image = (String) userHistorySnapshot.child("Image").getValue();
                    userHistory.put("Level", level);
                    userHistory.put("Count", count);
                    userHistory.put("Time", time);
                    userHistory.put("Date", date);
                    userHistory.put("Image",image);
                    System.out.println("Date: " + date + " Level: " + level + " Count: " + count + " Time: " + time);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return userHistory;
    }

}
