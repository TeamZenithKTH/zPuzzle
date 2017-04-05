package com.teamzenith.game.zpuzzle.controller;

import com.teamzenith.game.zpuzzle.dbhandler.GetUserInformation;
import com.teamzenith.game.zpuzzle.dbhandler.UserDAO;
import com.teamzenith.game.zpuzzle.model.User;

import java.text.ParseException;

/**
 * Created by alaaalkassar on 3/31/17.
 */

public class ProfileController {
    private User user;

    public void save(User user) throws ParseException {
        this.user = user;
        UserDAO userDAO = new UserDAO();
         userDAO.insertNewUser(user);
    }

    public void setToController(GetUserInformation getUserInformation, User user) {
        this.user = user;
        UserDAO userDAO =new UserDAO();
        userDAO.setListener(getUserInformation);
        userDAO.getUserInfo(user.getUserID());

    }
}
