package com.teamzenith.game.zpuzzle.dbhandler;

import com.teamzenith.game.zpuzzle.model.SendInvitation;

import java.util.HashMap;

/**
 * Created by alaaalkassar on 4/9/17.
 */

public interface GetMyFriendsChallengInvitations {
    public void getMyChallenges(HashMap<Integer, SendInvitation> getInvitation);
}
