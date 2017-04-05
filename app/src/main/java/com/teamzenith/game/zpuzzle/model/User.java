package com.teamzenith.game.zpuzzle.model;

import java.io.Serializable;

/**
 * Created by alaaalkassar on 3/31/17.
 */

public class User implements Serializable {
    private String userID;
    private String userName;
    private String userEmail;
    private String userImage;

    public User(String userID, String userName, String userEmail, String userImage) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userImage = userImage;
    }

    public User() {

    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
