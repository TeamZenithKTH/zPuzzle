package com.teamzenith.game.zpuzzle.model;

/**
 * Created by alaaalkassar on 3/31/17.
 */

public class UserHistoryEntry {
    private String userID;
    private String level;
    private String countMovementString;
    private String timerCounterString;
    private String imageFile;

    /**
     *
     */
    public UserHistoryEntry() {

    }

    /**
     *
     * @param userID
     * @param level
     * @param countMovementString
     * @param timerCounterString
     * @param imageFile
     */
    public UserHistoryEntry(String userID, Level level, String countMovementString, String timerCounterString, String imageFile) {
        this.userID = userID;
        this.level = level.toString();
        this.countMovementString = countMovementString;
        this.timerCounterString = timerCounterString;
        this.imageFile = imageFile;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCountMovementString() {
        return countMovementString;
    }

    public void setCountMovementString(String countMovementString) {
        this.countMovementString = countMovementString;
    }

    public String getTimerCounterString() {
        return timerCounterString;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setTimerCounterString(String timerCounterString) {
        this.timerCounterString = timerCounterString;
    }
}
