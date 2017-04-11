package com.teamzenith.game.zpuzzle.dbhandler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamzenith.game.zpuzzle.model.UsersNameID;

/**
 * Created by alaaalkassar on 4/8/17.
 */

public class GetUsersNamesIDsDAO {
    private DatabaseReference usersNamesIDsDatabase;
    private GetUsersNamesIDs getUsersNamesIDs;

    /**
     *
     */
    public void getUserNames() {
        usersNamesIDsDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference usersNameIDRef = usersNamesIDsDatabase.child("UsersNameID");
        usersNameIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userNameIDSnapshot : dataSnapshot.getChildren()) {
                    UsersNameID userNameIDEntry = userNameIDSnapshot.getValue(UsersNameID.class);
                    getUsersNamesIDs.get(userNameIDEntry);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     *
     * @param getUsersNamesIDs
     */
    public void setListener(GetUsersNamesIDs getUsersNamesIDs) {
        this.getUsersNamesIDs = getUsersNamesIDs;
    }

}
