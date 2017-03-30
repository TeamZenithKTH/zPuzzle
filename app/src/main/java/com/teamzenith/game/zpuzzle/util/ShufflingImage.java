package com.teamzenith.game.zpuzzle.util;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * This class will shuffle the input images randomly.
 * Created by alaaalkassar on 3/14/17.
 */

public class ShufflingImage {

    private HashMap<Integer, Bitmap> meMap = new HashMap<Integer, Bitmap>();

    /**
     * This method will shuffle the provided image's array.
     *
     * @param img An array that hold the images that will be shuffled.
     * @return tmpBmp An array of bitmaps that hold the newly shuffled images.
     */
    public Bitmap[] shuffle(Bitmap[] img) {

        Bitmap[] tmpBmp = new Bitmap[img.length];
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < img.length-1; i++) {//fixed
            list.add(i);
         // meMap.put(i, img[i]); //******** décomment
        }

        Random rand = new Random();
        int i = 0;
        while (list.size() > 0) {
            int index = rand.nextInt(list.size());
            tmpBmp[i] = img[list.remove(index)];

           meMap.put(i, tmpBmp[i]); //********* comment
            i++;
        }
        //Set the right lower corner in the matrix to be empty.
        tmpBmp[tmpBmp.length-1]=img[img.length-1]; //fixed
        meMap.put(tmpBmp.length-1,tmpBmp[tmpBmp.length-1]);//fixed
        return tmpBmp;
    }

    /**
     * Get the shuffled image's array.
     *
     * @return meMap The shuffled image's array.
     */
    public HashMap<Integer, Bitmap> getShuffledOrder() {
        return meMap;
    }

    /**
     * Set the new images array.
     *
     * @param newOrder
     */
}
