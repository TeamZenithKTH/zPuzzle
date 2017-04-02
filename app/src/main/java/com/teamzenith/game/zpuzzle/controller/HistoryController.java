package com.teamzenith.game.zpuzzle.controller;

import com.teamzenith.game.zpuzzle.dbhandler.UserDAO;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.text.ParseException;

/**
 * Created by alaaalkassar on 3/31/17.
 */
public class HistoryController {
    private UserHistoryEntry userHistoryEntry;

    public void save(UserHistoryEntry userHistoryEntry) throws ParseException {
        this.userHistoryEntry = userHistoryEntry;
        UserDAO userDAO = new UserDAO();
        userDAO.insertOnHistoryEntry(userHistoryEntry);
    }
}
