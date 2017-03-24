package com.teamzenith.game.zpuzzle.util;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by alaaalkassar on 3/15/17.
 */

public class MoveImage {
    private HashMap<Integer, Bitmap> SHMap = new HashMap<Integer, Bitmap>();
    private int position;
    private HashMap<Integer, Bitmap> newList;
    private String msg;

    /**
     * This method will be responseble of the moving of the images when the user click on an imageButton.
     *
     * @param SHMap   The Hashmap that holds the shuffled images and their postions after shuffling.
     * @param imageId The id of the clicked imageButton.
     * @param row     The matrix row number.
     * @param column  The matrix columns number.
     * @return newList the new order of the images after making the change according to the user change.
     * newList
     */

    public HashMap<Integer, Bitmap> step(HashMap<Integer, Bitmap> SHMap, int imageId, int row, int column) {
        //int j = 0;

        this.SHMap = SHMap;
        if (SHMap.get(imageId) == null) {
            msg = "YOU CANNOT MOVE EMPTY IMAGE";
            return newList = SHMap;
        } else {
            //Check if the left upper imageButton was clicked.
            if (imageId == 0) {
                if (SHMap.get(imageId + 1) == null) {
                    SHMap.put(imageId + 1, SHMap.get(imageId));
                    SHMap.put(imageId, null);
                    return newList = SHMap;
                } else if (SHMap.get(imageId + column) == null) {
                    SHMap.put(imageId + column, SHMap.get(imageId));
                    SHMap.put(imageId, null);
                    return newList = SHMap;
                } else {
                    msg = "THERE IS NO PLACE TO MOVE TO";
                    return newList = SHMap;
                }
                //Check if the right upper imageButton was clicked.
            } else if (imageId - column + 1 == 0) {
                if (SHMap.get(imageId - 1) == null) {
                    SHMap.put(imageId - 1, SHMap.get(imageId));
                    SHMap.put(imageId, null);
                    return newList = SHMap;
                } else if (SHMap.get(imageId + column) == null) {
                    SHMap.put(imageId + column, SHMap.get(imageId));
                    SHMap.put(imageId, null);
                    return newList = SHMap;
                } else {
                    msg = "THERE IS NO PLACE TO MOVE TO";
                    return newList = SHMap;
                }
                //Check if the left lower imageButton was clicked.
            } else if (imageId == (row * column) - column) {
                if (SHMap.get(imageId + 1) == null) {
                    SHMap.put(imageId + 1, SHMap.get(imageId));
                    SHMap.put(imageId, null);
                    return newList = SHMap;
                } else if (SHMap.get(imageId - column) == null) {
                    SHMap.put(imageId - column, SHMap.get(imageId));
                    SHMap.put(imageId, null);
                    return newList = SHMap;
                } else {
                    msg = "THERE IS NO PLACE TO MOVE TO";
                    return newList = SHMap;
                }

                //Check if the right lower imageButton was clicked.
            } else if (imageId == (row * column) - 1) {
                if (SHMap.get(imageId - 1) == null) {
                    SHMap.put(imageId - 1, SHMap.get(imageId));
                    SHMap.put(imageId, null);
                    return newList = SHMap;
                } else if (SHMap.get(imageId - column) == null) {
                    SHMap.put(imageId - column, SHMap.get(imageId));
                    SHMap.put(imageId, null);
                    return newList = SHMap;
                } else {
                    msg = "THERE IS NO PLACE TO MOVE TO";
                    return newList = SHMap;
                }
            }
            //check if an imageButton from the first column was clicked except the left upper imageButton and the left lower imageButton.
            for (int i = 1; i < row - 1; i++) {
                if (imageId == (i * row)) {
                    if (SHMap.get(imageId + 1) == null) {
                        SHMap.put(imageId + 1, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else if (SHMap.get(imageId - column) == null) {
                        SHMap.put(imageId - column, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else if (SHMap.get(imageId + column) == null) {
                        SHMap.put(imageId + column, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else {
                        msg = "THERE IS NO PLACE TO MOVE TO";
                        return newList = SHMap;
                    }

                }
            }
            //Check if an imageButton was clicked from the right column except for the right upper imageButton and the right lower imageButton.
            for (int i = 1; i < row - 1; i++) {
                if (imageId == (i * column) + column - 1) {
                    if (SHMap.get(imageId - 1) == null) {
                        SHMap.put(imageId - 1, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else if (SHMap.get(imageId - column) == null) {
                        SHMap.put(imageId - column, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else if (SHMap.get(imageId + column) == null) {
                        SHMap.put(imageId + column, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else {
                        msg = "THERE IS NO PLACE TO MOVE TO";
                        return newList = SHMap;
                    }
                }
            }
            //Check if an imageButton from the first row was clicked except for the left upper imageButton and the right upper imageButton.
            for (int i = 1; i < column - 1; i++) {
                if (imageId == i) {
                    if (SHMap.get(imageId - 1) == null) {
                        SHMap.put(imageId - 1, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else if (SHMap.get(imageId + 1) == null) {
                        SHMap.put(imageId + 1, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else if (SHMap.get(imageId + column) == null) {
                        SHMap.put(imageId + column, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else {
                        msg = "THERE IS NO PLACE TO MOVE TO";
                        return newList = SHMap;
                    }

                }
            }
            //Check if an imageButton from the last row was clicked except for the left lower imageButton and the right lower imageButton.
            for (int i = ((column * (row - 1)) + 1); i < (column * row) - 1; i++) {
                if (imageId == i) {
                    if (SHMap.get(imageId - 1) == null) {
                        SHMap.put(imageId - 1, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else if (SHMap.get(imageId + 1) == null) {
                        SHMap.put(imageId + 1, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else if (SHMap.get(imageId - column) == null) {
                        SHMap.put(imageId - column, SHMap.get(imageId));
                        SHMap.put(imageId, null);
                        return newList = SHMap;
                    } else {
                        msg = "THERE IS NO PLACE TO MOVE TO";
                        return newList = SHMap;
                    }
                }
            }


            //Check the rest of the imageButtons was clicked.

            if (SHMap.get(imageId - 1) == null) {
                SHMap.put(imageId - 1, SHMap.get(imageId));
                SHMap.put(imageId, null);
                return newList = SHMap;
            } else if (SHMap.get(imageId - column) == null) {
                SHMap.put(imageId - column, SHMap.get(imageId));
                SHMap.put(imageId, null);
                return newList = SHMap;
            } else if (SHMap.get(imageId + column) == null) {
                SHMap.put(imageId + column, SHMap.get(imageId));
                SHMap.put(imageId, null);
                return newList = SHMap;
            } else if (SHMap.get(imageId + 1) == null) {
                SHMap.put(imageId + 1, SHMap.get(imageId));
                SHMap.put(imageId, null);
                return newList = SHMap;
            } else {
                msg = "THERE IS NO PLACE TO MOVE TO";
                return newList = SHMap;
            }
        }
    }


    public String getMsg() {
        return msg;
    }

}