package com.teamzenith.game.zpuzzle.util;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by alaaalkassar on 3/16/17.
 */

public class GetCurrentStatus {
    Bitmap[] orignalStatus;
    HashMap<Integer, Bitmap> newStatus;
    private boolean status = false;

    /**
     * This method will check the image's Matrix for each move that the user do, and it will check if the user solved the puzzle or not.
     *
     * @param orignalStatus The original postion for the images befor shuffling
     * @param newStatus     The new postion for the images after each move.
     * @return true If the user solved the puzzle.
     * false if the user didn't solve the puzzle.
     */
    public boolean checkCurrentImage(Bitmap[] orignalStatus, HashMap<Integer, Bitmap> newStatus) {

        int count = 0;
        this.orignalStatus = orignalStatus;
        this.newStatus = newStatus;
        for (int index = 0; index < newStatus.size(); index++) {
            if (newStatus.get(index) != null && orignalStatus[index] != null && orignalStatus[index].equals(newStatus.get(index))) {

                // increase the number for each correct postion.
                count++;
                System.out.println(count);
            }
        }

        if (count == newStatus.size() - 1) {
            System.out.println("Done, You solved it");
            return status=true;
        } else {
            return status;
        }
    }
}
