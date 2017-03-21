package com.teamzenith.game.zpuzzle.util;

import com.teamzenith.game.zpuzzle.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alaaalkassar on 3/16/17.
 */

public class ImagesIDs {


    private HashMap<Integer, Integer> images= new HashMap<>();;

    /**
     *
     * @param imagID
     * @param position
     */
   public void setposition(int imagID, int position) {
       images.put(imagID, position);

   }

    /**
     *
     * @return
     */
    public HashMap<Integer,Integer> getposition() {

        return images;
    }
}
