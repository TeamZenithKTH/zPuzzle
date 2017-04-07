package com.teamzenith.game.zpuzzle.controller;

import com.teamzenith.game.zpuzzle.dbhandler.SendInvitationDAO;
import com.teamzenith.game.zpuzzle.model.SendInvitation;

import java.text.ParseException;

/**
 * Created by alaaalkassar on 4/7/17.
 */

public class InvetationsController {
    private SendInvitation sendInvitation;
    private String userID;
    public void sendInvitationToUser(SendInvitation sendInvitation, String userID) throws ParseException {
        this.sendInvitation = sendInvitation;
        this.userID= userID;
        SendInvitationDAO sendInvitationDAO = new SendInvitationDAO();
        sendInvitationDAO.send(sendInvitation,userID);
    }
}
