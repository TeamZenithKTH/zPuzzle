package com.teamzenith.game.zpuzzle.dbhandler;

import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.util.HashMap;

/**
 * Created by alaaalkassar on 4/6/17.
 */

public interface GetUserHistory {
    public void get(HashMap<Integer, UserHistoryEntry> userHistoryEntry);
}
