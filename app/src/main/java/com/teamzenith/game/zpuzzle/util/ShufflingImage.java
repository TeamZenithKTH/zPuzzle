package com.teamzenith.game.zpuzzle.util;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by alaaalkassar on 3/14/17.
 */

public class ShufflingImage {

    private HashMap<Integer, Bitmap> meMap=new HashMap<Integer, Bitmap>();

    /**
     *
     * @param img
     * @return
     */
    public Bitmap[] shuffle(Bitmap[] img){

        //HashMap<Integer, Bitmap> meMap=new HashMap<Integer, Bitmap>();
        Bitmap[] tmpBmp=new Bitmap[img.length];

        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < img.length; i++) {
            list.add(i);
        }

        Random rand = new Random();
        int i=0;
        while(list.size() > 0) {

            int index = rand.nextInt(list.size());
            tmpBmp[i]=img[list.remove(index)];
            Bitmap resized = Bitmap.createScaledBitmap(tmpBmp[i], 250, 250, true);

            meMap.put(i,resized);
            i++;
        }
        meMap.put(img.length-1,null);

        return tmpBmp;
    }

    /**
     *
     * @return
     */
    public HashMap<Integer, Bitmap> getShuffledOrder(){


        return meMap;
    }

    /**
     *
     * @param newOrder
     */

    public void setShuffledOrder(HashMap<Integer, Bitmap> newOrder){
        this.meMap = newOrder;
    }
}
