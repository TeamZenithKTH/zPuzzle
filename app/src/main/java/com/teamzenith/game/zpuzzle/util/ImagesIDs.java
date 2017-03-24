package com.teamzenith.game.zpuzzle.util;

import java.util.HashMap;

/**
 * Created by alaaalkassar on 3/16/17.
 */

public class ImagesIDs {


    private HashMap<Integer, Integer> images = new HashMap<>();

    /**
     * Set the postion of the image pieces in a HashMap to use it later.
     *
     * @param imagID   Get the image id
     * @param position Set the image position in the HashMap.
     */
    public void setposition(int imagID, int position) {
        images.put(imagID, position);

    }

    /**
     * Get the postion of the image pieces.
     *
     * @return Return a HashMap that include the imageButton's ids and the image's positions.
     */
    public HashMap<Integer, Integer> getposition() {

        return images;
    }
}
