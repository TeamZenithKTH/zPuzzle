package com.teamzenith.game.zpuzzle.model;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by alaaalkassar on 3/31/17.
 */

public class UserHistoryEntry implements Comparable<UserHistoryEntry>{
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

    private static Float convertTimeToSeconds(String s) {

        String[] units = s.split(":"); //will break the string up into an array
        Float hour = Float.parseFloat(units[0]); //first element
        Float minutes = Float.parseFloat(units[1]); //second element
        Float seconds = Float.parseFloat(units[2]);
        Float millis = Float.parseFloat(units[3]);

        Float duration = (millis / 1000) + (60 * minutes) + seconds + (hour * 3600); //add up our values


        return duration;
    }




    @Override
    public int compareTo(@NonNull UserHistoryEntry o) {

            return Float.compare(convertTimeToSeconds(this.getTimerCounterString()), convertTimeToSeconds(o.getTimerCounterString()));

    }
}
