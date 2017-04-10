package com.teamzenith.game.zpuzzle.dbhandler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by alaaalkassar on 4/5/17.
 */

public class HistoryDAO {
    private DatabaseReference mDatabase;
    private DatabaseReference historyDatabase, usersDB;
    private String date;
    private Level level;
    private String userID;
    private String countMovementString;
    private String timerCounterString;
    private UserHistoryEntry userHistoryEntry;
    private HashMap<Integer, UserHistoryEntry> oneHistoryEntry;
    private HashMap<Integer, UserHistoryEntry> AllHistoryEntry;
    private GetUserHistory getUserHistory;
    private DatabaseReference ref;

    /**
     * @param userHistoryEntry
     * @throws ParseException
     */
    public void insertOnHistoryEntry(UserHistoryEntry userHistoryEntry) throws ParseException {
        this.userHistoryEntry = userHistoryEntry;
        this.userID = userHistoryEntry.getUserID();
        date = date();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = mDatabase.child("History").child(userID).child(date);
        usersRef.setValue(userHistoryEntry);
    }

    private String date() throws ParseException {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }

    private void getUserHistory(String userID) {
        oneHistoryEntry = new HashMap<>();
        historyDatabase = FirebaseDatabase.getInstance().getReference().child("History");
        final DatabaseReference historyRef = historyDatabase.child(userID);
        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long num = dataSnapshot.getChildrenCount();
                int i = 0;
                for (DataSnapshot userHistorySnapshot : dataSnapshot.getChildren()) {
                    UserHistoryEntry userHistoryEntry = userHistorySnapshot.getValue(UserHistoryEntry.class);
                    oneHistoryEntry.put(i, userHistoryEntry);
                    i++;
                }
                getUserHistory.get(oneHistoryEntry);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     *
     * @param userID
     */
    public void getHistoryEntry(String userID) {
        getUserHistory(userID);
    }

    /**
     *
     * @param getUserHistory
     */
    public void setListener(GetUserHistory getUserHistory) {
        this.getUserHistory = getUserHistory;
    }


}
