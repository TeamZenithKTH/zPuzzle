package com.teamzenith.game.zpuzzle.model;

/**
 * Created by alaaalkassar on 4/7/17.
 */

public class SendInvitation {
    private String intiationText;
    private String imageURL;
    private String level;
    private String userID;
    private String presentText;
    private boolean status;
    public SendInvitation(String intiationText, String imageURL, String level, String userID, String presentText,boolean status) {
        this.intiationText = intiationText;
        this.imageURL = imageURL;
        this.level = level;
        this.userID = userID;
        this.presentText = presentText;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getIntiationText() {
        return intiationText;
    }

    public void setIntiationText(String intiationText) {
        this.intiationText = intiationText;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPresentText() {
        return presentText;
    }

    public void setPresentText(String presentText) {
        this.presentText = presentText;
    }
}
