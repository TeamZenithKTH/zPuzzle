package com.teamzenith.game.zpuzzle.util;

import android.content.Intent;

import com.teamzenith.game.zpuzzle.controller.ImageChooser;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.SendInvitation;
import com.teamzenith.game.zpuzzle.model.User;

import java.io.File;

/**
 * Created by alaaalkassar on 4/10/17.
 */

public class InvitationGame {
    private SendInvitation sendInvitation;
    private String invitationLetter, presentLetter, level, senderName, imageURL;
    private boolean status;
    private Level gameLevel;
    public InvitationGame(SendInvitation sendInvitation) {
        this.sendInvitation = sendInvitation;
        this.invitationLetter = sendInvitation.getIntiationText();
        this.presentLetter = sendInvitation.getPresentText();
        this.senderName = sendInvitation.getUserID();
        this.imageURL = sendInvitation.getImageURL();
        this.status = sendInvitation.isStatus();
        this.level = sendInvitation.getLevel();
    }
    private void initComponent() {

    }

}
