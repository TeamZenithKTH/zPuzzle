package com.teamzenith.game.zpuzzle.dbhandler;

import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamzenith.game.zpuzzle.controller.TopGamesActivity;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

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
    private TopGamesActivity topGamesActivity;


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
        //return userHistory;
    }

    public void getAllUsersHistory() {
        AllHistoryEntry = new HashMap<>();
        historyDatabase = FirebaseDatabase.getInstance().getReference().child("History");
      //  final DatabaseReference historyRef = historyDatabase.child(userID);
        historyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long num = dataSnapshot.getChildrenCount();

                for (DataSnapshot userHistorySnapshot : dataSnapshot.getChildren()) {

                    String userId = userHistorySnapshot.getKey();
                    final DatabaseReference historyRef = historyDatabase.child(userId);
                    historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot userHistorySnapshot : dataSnapshot.getChildren()) {
                                UserHistoryEntry userHistoryEntry = userHistorySnapshot.getValue(UserHistoryEntry.class);

                                topGamesActivity.getAll(userHistoryEntry);

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getHistoryEntry(String userID) {
        getUserHistory(userID);
    }

    public void setListener(GetUserHistory getUserHistory) {
        this.getUserHistory = getUserHistory;
    }





    public void setListener(TopGamesActivity topGamesActivity) {
        this.topGamesActivity=topGamesActivity;
    }
}
