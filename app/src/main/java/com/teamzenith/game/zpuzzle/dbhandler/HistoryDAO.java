package com.teamzenith.game.zpuzzle.dbhandler;

import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by alaaalkassar on 4/5/17.
 */

public class HistoryDAO{
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

    public void getAllUsersHistory(final Level s) {


         AtomicInteger count = new AtomicInteger();
        final int[] hardCount = new int[1];
        hardCount[0]=0;
        final int[] mediumCount = new int[1];
        mediumCount[0]=0;
        final int[] easyCount = new int[1];
        easyCount[0]=0;
        ref = FirebaseDatabase.getInstance().getReference().child("History");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                // New child added, increment count


                Map<String, UserHistoryEntry> td = (Map<String, UserHistoryEntry>) dataSnapshot.getValue();
                for (Map.Entry<String, UserHistoryEntry> entry : td.entrySet()) {
                    if (entry.getValue().getLevel().equals("Hard")) {
                        hardCount[0] =  hardCount[0]+ 1;
                    }
                    else if(entry.getValue().getLevel().equals("Medium")){
                        mediumCount[0] =  mediumCount[0]+ 1;
                    }
                    else
                        easyCount[0] =  easyCount[0]+ 1;
                }

                //notifyDataSetChanged();
            }
               /* UserHistoryEntry userHistoryEntry= (UserHistoryEntry) dataSnapshot.getValue();
                if(userHistoryEntry.getLevel().equals("Hard")){

                            //dataSnapshot.getChildrenCount());
                }
                else if(userHistoryEntry.getLevel().equals("Medium")){
                    mediumCount[0] =  mediumCount[0]+ 1;
                }
                else
                    easyCount[0] =  easyCount[0]+ 1;
                System.out.println("Added " + dataSnapshot.getKey() + ", count is " + easyCount[0]);
            }*/

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            // ...
        });
        AllHistoryEntry = new HashMap<>();
        historyDatabase = FirebaseDatabase.getInstance().getReference().child("History");
        //  final DatabaseReference historyRef = historyDatabase.child(userID);
        historyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userHistorySnapshot : dataSnapshot.getChildren()) {

                    String userId = userHistorySnapshot.getKey();
                    final DatabaseReference historyRef = historyDatabase.child(userId);
                    historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot userHistorySnapshot : dataSnapshot.getChildren()) {
                                UserHistoryEntry userHistoryEntry = userHistorySnapshot.getValue(UserHistoryEntry.class);

                                if (userHistoryEntry.getLevel().equals(s.toString())) {
                                    System.out.println(userHistoryEntry.getLevel());
                                    topGamesActivity.getAll(userHistoryEntry,hardCount[0]);
                                }
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
        this.topGamesActivity = topGamesActivity;
    }

}
