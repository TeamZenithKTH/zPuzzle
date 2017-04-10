package com.teamzenith.game.zpuzzle.controller;

import android.widget.ImageView;

import com.teamzenith.game.zpuzzle.dbhandler.GetImageURL;
import com.teamzenith.game.zpuzzle.dbhandler.GetUserHistory;
import com.teamzenith.game.zpuzzle.dbhandler.HistoryDAO;
import com.teamzenith.game.zpuzzle.dbhandler.UploadToDatabase;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.text.ParseException;

/**
 * Created by alaaalkassar on 3/31/17.
 */
public class HistoryController {
    private UserHistoryEntry userHistoryEntry;
    private User user;

    /**
     * This method will save the newly signed up users to the database.
     *
     * @param userHistoryEntry This object will contatine all the information about the new user.
     * @throws ParseException
     */
    public void save(UserHistoryEntry userHistoryEntry) throws ParseException {
        this.userHistoryEntry = userHistoryEntry;
        HistoryDAO historyDAO = new HistoryDAO();
        historyDAO.insertOnHistoryEntry(userHistoryEntry);

    }

    /**
     * This image will save the user's new image to the database.
     *
     * @param imageView   The imageView that contatin the new image for the user
     * @param getImageURL The image link in the database.
     * @param userId      The user ID.
     */

    public void setToControllerFromAfterTheGameActivity(ImageView imageView, GetImageURL getImageURL, String userId) {

        UploadToDatabase uploadToDatabase = new UploadToDatabase();
        uploadToDatabase.setListener(getImageURL, imageView);
        uploadToDatabase.upload(userId);


    }

    /**
     * This method will get all history of the user.
     *
     * @param getUserHistory Interface that will get the user's history.
     * @param userId         The user ID.
     */
    public void setToControllerHistoryActivity(GetUserHistory getUserHistory, String userId) {
        HistoryDAO historyDAO = new HistoryDAO();
        historyDAO.setListener(getUserHistory);
        historyDAO.getHistoryEntry(userId);
    }
}
