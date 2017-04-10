package com.teamzenith.game.zpuzzle.util;

import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.SendInvitation;

/**
 * Created by alaaalkassar on 4/10/17.
 */

public class InvitationGame {
    private SendInvitation sendInvitation;
    private String invitationLetter, presentLetter, level, senderName, imageURL;
    private boolean status;
    private Level gameLevel;

    /**
     * @param sendInvitation
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public InvitationGame(SendInvitation sendInvitation) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.sendInvitation = sendInvitation;
        this.invitationLetter = sendInvitation.getIntiationText();
        this.presentLetter = sendInvitation.getPresentText();
        this.senderName = sendInvitation.getUserID();
        this.imageURL = sendInvitation.getImageURL();
        this.status = sendInvitation.isStatus();
        this.level = sendInvitation.getLevel();
        Class cls = Class.forName(Level.class.getPackage().getName() + "." + this.level);
        Level myTestObject = (Level) cls.newInstance();
    }

    private void initComponent() {

    }

}
