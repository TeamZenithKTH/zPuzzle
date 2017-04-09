package com.teamzenith.game.zpuzzle.controller;

import android.widget.ImageView;

import com.teamzenith.game.zpuzzle.dbhandler.GetUserInvitations;
import com.teamzenith.game.zpuzzle.dbhandler.GetUsersNamesIDs;
import com.teamzenith.game.zpuzzle.dbhandler.GetUsersNamesIDsDAO;
import com.teamzenith.game.zpuzzle.dbhandler.SendInvitationDAO;
import com.teamzenith.game.zpuzzle.dbhandler.SendInvitationToUser;
import com.teamzenith.game.zpuzzle.dbhandler.UploadToDatabase;
import com.teamzenith.game.zpuzzle.model.SendInvitation;

import java.text.ParseException;

/**
 * Created by alaaalkassar on 4/7/17.
 */

public class InvitationsController {
    private SendInvitation sendInvitation;
    private String userID;

    public void sendInvitationToUser(ImageView userImageView, SendInvitationToUser sendInvitationToUser, String userID) throws ParseException {
        this.userID = userID;
        UploadToDatabase uploadToDatabase = new UploadToDatabase();
        uploadToDatabase.setListenerUserInvitationImage(sendInvitationToUser, userImageView);
        uploadToDatabase.uploadUserInvitationImage(userID);
    }

    public void send(SendInvitation sendInvitation, String userID) throws ParseException {
        SendInvitationDAO sendInvitationDAO = new SendInvitationDAO();
        sendInvitationDAO.send(sendInvitation, userID);
        sendInvitationDAO.sendToFriend(sendInvitation,userID);
    }

    public void getUsersNames(GetUsersNamesIDs getUsersNamesIDs) {
        GetUsersNamesIDsDAO getUsersNamesIDsDAO = new GetUsersNamesIDsDAO();
        getUsersNamesIDsDAO.setListener(getUsersNamesIDs);
        getUsersNamesIDsDAO.getUserNames();
    }
    public void getUserInvitaionsHistory(GetUserInvitations getUserInvitations, String userID){
        this.userID = userID;
        SendInvitationDAO sendInvitationDAO = new SendInvitationDAO();
        sendInvitationDAO.setListener(getUserInvitations);
        sendInvitationDAO.getMySentInvitations(userID);
    }
}
