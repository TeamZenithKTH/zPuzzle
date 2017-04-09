package com.teamzenith.game.zpuzzle.model;

/**
 * Created by alaaalkassar on 4/8/17.
 */

public class UsersNameID {
    private String userID;
    private String userName;

    public UsersNameID(String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public UsersNameID() {

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
}
