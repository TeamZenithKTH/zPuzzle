package com.teamzenith.game.zpuzzle.controller;

import com.teamzenith.game.zpuzzle.dbhandler.UserDAO;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

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
}
