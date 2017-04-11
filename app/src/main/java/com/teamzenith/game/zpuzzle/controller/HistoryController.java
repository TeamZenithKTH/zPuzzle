package com.teamzenith.game.zpuzzle.controller;

import android.widget.ImageView;

import com.teamzenith.game.zpuzzle.dbhandler.GetImageURL;
import com.teamzenith.game.zpuzzle.dbhandler.GetUserHistory;
import com.teamzenith.game.zpuzzle.dbhandler.HistoryDAO;
import com.teamzenith.game.zpuzzle.dbhandler.UploadToDatabase;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.text.ParseException;

/**
 * Created by alaaalkassar on 3/31/17.
 */
public class HistoryController {
    private UserHistoryEntry userHistoryEntry;
    private User user;

    public void save(UserHistoryEntry userHistoryEntry) throws ParseException {
        this.userHistoryEntry = userHistoryEntry;
        HistoryDAO historyDAO = new HistoryDAO();
        historyDAO.insertOnHistoryEntry(userHistoryEntry);

    }


    public void setToControllerFromAfterTheGameActivity(ImageView imageView, GetImageURL getImageURL, String userId) {

        UploadToDatabase uploadToDatabase = new UploadToDatabase();
        uploadToDatabase.setListener(getImageURL, imageView);
        uploadToDatabase.upload(userId);


    }

    public void setToControllerHistoryActivity(GetUserHistory getUserHistory, String userId) {
        HistoryDAO historyDAO = new HistoryDAO();
        historyDAO.setListener(getUserHistory);
        historyDAO.getHistoryEntry(userId);
    }

    public void setToControllerFromToGamesActivity(TopGamesActivity topGamesActivity, Level s) {
        HistoryDAO historyDAO=new HistoryDAO();
        historyDAO.setListener(topGamesActivity);
        historyDAO.getAllUsersHistory(s);


    }
}
