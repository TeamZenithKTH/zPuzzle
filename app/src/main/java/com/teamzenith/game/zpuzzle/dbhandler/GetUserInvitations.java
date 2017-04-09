package com.teamzenith.game.zpuzzle.dbhandler;

import com.teamzenith.game.zpuzzle.model.SendInvitation;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.util.HashMap;

/**
 * Created by alaaalkassar on 4/8/17.
 */

public interface GetUserInvitations {
    public void get(HashMap<Integer, SendInvitation> sendInvitation);
}
