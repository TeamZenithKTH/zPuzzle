package com.teamzenith.game.zpuzzle.controller;

import android.widget.ImageView;

import com.teamzenith.game.zpuzzle.dbhandler.GetUserInformation;
import com.teamzenith.game.zpuzzle.dbhandler.UpdateUserImage;
import com.teamzenith.game.zpuzzle.dbhandler.UploadToDatabase;
import com.teamzenith.game.zpuzzle.dbhandler.UserDAO;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UsersNameID;

import java.text.ParseException;

/**
 * Created by alaaalkassar on 3/31/17.
 */

public class ProfileController {
    private User user;

    /**
     * @param user
     * @param usersNameID
     * @throws ParseException
     */
    public void save(User user, UsersNameID usersNameID) throws ParseException {
        this.user = user;
        UserDAO userDAO = new UserDAO();
        userDAO.insertNewUser(user, usersNameID);
        userDAO.insertUserNameID(usersNameID);
    }

    /**
     * @param getUserInformation
     * @param user
     */
    public void setToController(GetUserInformation getUserInformation, User user) {
        this.user = user;
        UserDAO userDAO = new UserDAO();
        userDAO.setListener(getUserInformation);
        userDAO.getUserInfo(user.getUserID());

    }

    /**
     * @param userImageView
     * @param updateUserImage
     * @param userID
     */
    public void setToControllerFromProfileActivity(ImageView userImageView, UpdateUserImage updateUserImage, String userID) {
        UploadToDatabase uploadToDatabase = new UploadToDatabase();
        uploadToDatabase.setListenerUpdateImage(updateUserImage, userImageView);
        uploadToDatabase.uploadUserImage(userID);
    }
}
