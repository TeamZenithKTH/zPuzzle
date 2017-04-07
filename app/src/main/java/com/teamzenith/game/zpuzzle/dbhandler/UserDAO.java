package com.teamzenith.game.zpuzzle.dbhandler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamzenith.game.zpuzzle.model.User;


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


    public void insertNewUser(User user) {
        this.user = user;
        this.userID = user.getUserID();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = mDatabase.child("Users").child(userID);
        usersRef.setValue(user);
    }

    public void setUserName(String uid, String userName) {
        this.userID = uid;
        this.userName = userName;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference insertUserName = ref.child("Users").child(userID).child("userName");
        insertUserName.setValue(userName);
    }

    public void setUserImage(String uid, String userImage) {
        this.userID = uid;
        this.userImage = userImage;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference insertUserImage = ref.child("Users").child(userID).child("userImage");
        insertUserImage.setValue(userImage);
    }

    public void setUserEmail(String uid, String userEmail) {
        this.userID = uid;
        this.userEmail = userImage;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference insertUserName = ref.child("Users").child(userID).child("userEmail");
        insertUserName.setValue(userEmail);
    }

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

    public void setListener(GetUserInformation getUserInformation) {
        this.getUserInformation = getUserInformation;
    }
}
