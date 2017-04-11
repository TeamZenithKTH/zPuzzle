package com.teamzenith.game.zpuzzle.dbhandler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UsersNameID;


/**
 * Created by alaaalkassar on 3/31/17.
 */

public class UserDAO {
    private DatabaseReference mDatabase;
    private String userID;
    private User user;
    private String userName;
    private String userEmail;
    private String userImage;
    private String imageFile;
    private GetUserInformation getUserInformation;
    private UsersNameID usersNameID;

    /**
     *
     * @param user
     * @param usersNameID
     */
    public void insertNewUser(User user, UsersNameID usersNameID) {
        this.user = user;
        this.userID = user.getUserID();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = mDatabase.child("Users").child(userID);
        DatabaseReference allUsersNamesAndIDs = mDatabase.child("UsersNameID").child(userID).child("userName");
        allUsersNamesAndIDs.setValue(usersNameID.getUserName());
        usersRef.setValue(user);
    }

    /**
     *
     * @param usersNameID
     */
    public void insertUserNameID(UsersNameID usersNameID) {
        this.usersNameID = usersNameID;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference allUsersNamesAndIDs = mDatabase.child("UsersNameID").child(usersNameID.getUserID());
        allUsersNamesAndIDs.setValue(usersNameID);

    }

    /**
     * @param uid
     */
    public void getUserInfo(String uid) {
        this.userID = uid;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference getUserInfo = ref.child("Users").child(userID);
        getUserInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                getUserInformation.get(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * @param getUserInformation
     */
    public void setListener(GetUserInformation getUserInformation) {
        this.getUserInformation = getUserInformation;
    }
}
