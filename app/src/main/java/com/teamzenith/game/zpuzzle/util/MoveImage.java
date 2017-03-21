package com.teamzenith.game.zpuzzle.util;

import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.HashMap;

/**
 * Created by alaaalkassar on 3/15/17.
 */

public class MoveImage {
    private HashMap<Integer, Bitmap> SHMap = new HashMap<Integer, Bitmap>();
    private int position;
    private HashMap<Integer, Bitmap> newList;

    /**
     *
     * @param SHMap
     * @param imageId
     * @return
     */
    public HashMap<Integer, Bitmap> move(HashMap<Integer, Bitmap> SHMap,int imageId) {
        this.SHMap = SHMap;
        position=imageId;
        if (SHMap.get(position) == null) {
            System.out.println("YOU CANNOT MOVE NULL IMAGE");
            return SHMap;
        } else if (SHMap.size() == 4) {
            SHMap = kidsNewOrder(position);
            return SHMap;
        } else if (SHMap.size() == 9) {
            SHMap = mediumNewOrder(position);
            return SHMap;
        } else if (SHMap.size()== 16){
            SHMap = hardNewOrder(position);
            return SHMap;
        }else {
            return SHMap;
        }
    }


    private HashMap<Integer, Bitmap> kidsNewOrder(int pos) {
        switch (pos) {
            case 0: {
                if (SHMap.get(1) == null) {
                    SHMap.put(1, SHMap.get(0));
                    SHMap.put(0, null);
                    return newList = SHMap;
                } else if (SHMap.get(2) == null) {
                    SHMap.put(2, SHMap.get(0));
                    SHMap.put(0, null);
                    return newList = SHMap;
                } else if (SHMap.get(3) == null) {
                    SHMap.put(3, SHMap.get(0));
                    SHMap.put(0, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 1: {
                if (SHMap.get(0) == null) {
                    SHMap.put(0, SHMap.get(1));
                    SHMap.put(1, null);
                    return newList = SHMap;
                } else if (SHMap.get(3) == null) {
                    SHMap.put(3, SHMap.get(1));
                    SHMap.put(1, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 2: {
                if (SHMap.get(0) == null) {
                    SHMap.put(0, SHMap.get(2));
                    SHMap.put(2, null);
                    return newList = SHMap;
                } else if (SHMap.get(3) == null) {
                    SHMap.put(3, SHMap.get(2));
                    SHMap.put(2, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 3: {
                if (SHMap.get(1) == null) {
                    SHMap.put(1, SHMap.get(3));
                    SHMap.put(3, null);
                    return newList = SHMap;
                } else if (SHMap.get(2) == null) {
                    SHMap.put(2, SHMap.get(3));
                    SHMap.put(3, null);
                    return newList = SHMap;
                }else if (SHMap.get(0) == null) {
                    SHMap.put(0, SHMap.get(3));
                    SHMap.put(3, null);
                    return newList = SHMap;
                }else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }

        }
        return newList;
    }
    private HashMap<Integer, Bitmap> mediumNewOrder(int pos) {
        switch (pos) {
            case 0: {
                if (SHMap.get(1) == null) {
                    SHMap.put(1, SHMap.get(0));
                    SHMap.put(0, null);
                    return newList = SHMap;
                } else if (SHMap.get(3) == null) {
                    SHMap.put(3, SHMap.get(0));
                    SHMap.put(0, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 1: {
                if (SHMap.get(0) == null) {
                    SHMap.put(0, SHMap.get(1));
                    SHMap.put(1, null);
                    return newList = SHMap;
                } else if (SHMap.get(4) == null) {
                    SHMap.put(4, SHMap.get(1));
                    SHMap.put(1, null);
                    return newList = SHMap;
                } else if (SHMap.get(2) == null) {
                    SHMap.put(2, SHMap.get(1));
                    SHMap.put(1, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 2: {
                if (SHMap.get(1) == null) {
                    SHMap.put(1, SHMap.get(2));
                    SHMap.put(2, null);
                    return newList = SHMap;
                } else if (SHMap.get(5) == null) {
                    SHMap.put(5, SHMap.get(2));
                    SHMap.put(2, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 3: {
                if (SHMap.get(0) == null) {
                    SHMap.put(0, SHMap.get(3));
                    SHMap.put(3, null);
                    return newList = SHMap;
                } else if (SHMap.get(4) == null) {
                    SHMap.put(4, SHMap.get(3));
                    SHMap.put(3, null);
                    return newList = SHMap;
                } else if (SHMap.get(6) == null) {
                    SHMap.put(6, SHMap.get(3));
                    SHMap.put(3, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 4: {
                if (SHMap.get(1) == null) {
                    SHMap.put(1, SHMap.get(4));
                    SHMap.put(4, null);
                    return newList = SHMap;
                } else if (SHMap.get(3) == null) {
                    SHMap.put(3, SHMap.get(4));
                    SHMap.put(4, null);
                    return newList = SHMap;
                } else if (SHMap.get(5) == null) {
                    SHMap.put(5, SHMap.get(4));
                    SHMap.put(4, null);
                    return newList = SHMap;
                } else if (SHMap.get(7) == null) {
                    SHMap.put(7, SHMap.get(4));
                    SHMap.put(4, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 5: {
                if (SHMap.get(2) == null) {
                    SHMap.put(2, SHMap.get(5));
                    SHMap.put(5, null);
                    return newList = SHMap;
                } else if (SHMap.get(4) == null) {
                    SHMap.put(4, SHMap.get(5));
                    SHMap.put(5, null);
                    return newList = SHMap;
                } else if (SHMap.get(8) == null) {
                    SHMap.put(8, SHMap.get(5));
                    SHMap.put(5, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 6: {
                if (SHMap.get(3) == null) {
                    SHMap.put(3, SHMap.get(6));
                    SHMap.put(6, null);
                    return newList = SHMap;
                } else if (SHMap.get(7) == null) {
                    SHMap.put(7, SHMap.get(6));
                    SHMap.put(6, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 7: {
                if (SHMap.get(4) == null) {
                    SHMap.put(4, SHMap.get(7));
                    SHMap.put(7, null);
                    return newList = SHMap;
                } else if (SHMap.get(6) == null) {
                    SHMap.put(6, SHMap.get(7));
                    SHMap.put(7, null);
                    return newList = SHMap;
                } else if (SHMap.get(8) == null) {
                    SHMap.put(8, SHMap.get(7));
                    SHMap.put(7, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 8: {
                if (SHMap.get(5) == null) {
                    SHMap.put(5, SHMap.get(8));
                    SHMap.put(8, null);
                    return newList = SHMap;
                } else if (SHMap.get(7) == null) {
                    SHMap.put(7, SHMap.get(8));
                    SHMap.put(8, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
        }

        return newList;
    }
    private HashMap<Integer, Bitmap> hardNewOrder(int pos) {
        switch (pos) {
            case 0: {
                if (SHMap.get(1) == null) {
                    SHMap.put(1, SHMap.get(0));
                    SHMap.put(0, null);
                    return newList = SHMap;
                } else if (SHMap.get(4) == null) {
                    SHMap.put(4, SHMap.get(0));
                    SHMap.put(0, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 1: {
                if (SHMap.get(0) == null) {
                    SHMap.put(0, SHMap.get(1));
                    SHMap.put(1, null);
                    return newList = SHMap;
                } else if (SHMap.get(2) == null) {
                    SHMap.put(2, SHMap.get(1));
                    SHMap.put(1, null);
                    return newList = SHMap;
                } else if (SHMap.get(5) == null) {
                    SHMap.put(5, SHMap.get(1));
                    SHMap.put(1, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 2: {
                if (SHMap.get(1) == null) {
                    SHMap.put(1, SHMap.get(2));
                    SHMap.put(2, null);
                    return newList = SHMap;
                } else if (SHMap.get(3) == null) {
                    SHMap.put(3, SHMap.get(2));
                    SHMap.put(2, null);
                    return newList = SHMap;
                } else if (SHMap.get(6) == null) {
                    SHMap.put(6, SHMap.get(2));
                    SHMap.put(2, null);
                    return newList = SHMap;
                }else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 3: {
                if (SHMap.get(2) == null) {
                    SHMap.put(2, SHMap.get(3));
                    SHMap.put(3, null);
                    return newList = SHMap;
                } else if (SHMap.get(7) == null) {
                    SHMap.put(7, SHMap.get(3));
                    SHMap.put(3, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 4: {
                if (SHMap.get(0) == null) {
                    SHMap.put(0, SHMap.get(4));
                    SHMap.put(4, null);
                    return newList = SHMap;
                } else if (SHMap.get(5) == null) {
                    SHMap.put(5, SHMap.get(4));
                    SHMap.put(4, null);
                    return newList = SHMap;
                } else if (SHMap.get(8) == null) {
                    SHMap.put(8, SHMap.get(4));
                    SHMap.put(4, null);
                    return newList = SHMap;
                }  else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 5: {
                if (SHMap.get(1) == null) {
                    SHMap.put(1, SHMap.get(5));
                    SHMap.put(5, null);
                    return newList = SHMap;
                } else if (SHMap.get(4) == null) {
                    SHMap.put(4, SHMap.get(5));
                    SHMap.put(5, null);
                    return newList = SHMap;
                } else if (SHMap.get(6) == null) {
                    SHMap.put(6, SHMap.get(5));
                    SHMap.put(5, null);
                    return newList = SHMap;
                } else if (SHMap.get(9) == null) {
                    SHMap.put(9, SHMap.get(5));
                    SHMap.put(5, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 6: {
                if (SHMap.get(2) == null) {
                    SHMap.put(2, SHMap.get(6));
                    SHMap.put(6, null);
                    return newList = SHMap;
                } else if (SHMap.get(7) == null) {
                    SHMap.put(7, SHMap.get(6));
                    SHMap.put(6, null);
                    return newList = SHMap;
                } else if (SHMap.get(5) == null) {
                    SHMap.put(5, SHMap.get(6));
                    SHMap.put(6, null);
                    return newList = SHMap;
                } else if (SHMap.get(10) == null) {
                    SHMap.put(10, SHMap.get(6));
                    SHMap.put(6, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 7: {
                if (SHMap.get(3) == null) {
                    SHMap.put(3, SHMap.get(7));
                    SHMap.put(7, null);
                    return newList = SHMap;
                } else if (SHMap.get(6) == null) {
                    SHMap.put(6, SHMap.get(7));
                    SHMap.put(7, null);
                    return newList = SHMap;
                } else if (SHMap.get(11) == null) {
                    SHMap.put(11, SHMap.get(7));
                    SHMap.put(7, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 8: {
                if (SHMap.get(4) == null) {
                    SHMap.put(4, SHMap.get(8));
                    SHMap.put(8, null);
                    return newList = SHMap;
                } else if (SHMap.get(9) == null) {
                    SHMap.put(9, SHMap.get(8));
                    SHMap.put(8, null);
                    return newList = SHMap;
                }else if (SHMap.get(12) == null) {
                    SHMap.put(12, SHMap.get(8));
                    SHMap.put(8, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 9: {
                if (SHMap.get(5) == null) {
                    SHMap.put(5, SHMap.get(9));
                    SHMap.put(9, null);
                    return newList = SHMap;
                } else if (SHMap.get(8) == null) {
                    SHMap.put(8, SHMap.get(9));
                    SHMap.put(9, null);
                    return newList = SHMap;
                } else if (SHMap.get(10) == null) {
                    SHMap.put(10, SHMap.get(9));
                    SHMap.put(9, null);
                    return newList = SHMap;
                }else if (SHMap.get(13) == null) {
                    SHMap.put(13, SHMap.get(9));
                    SHMap.put(9, null);
                    return newList = SHMap;
                }else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 10: {
                if (SHMap.get(6) == null) {
                    SHMap.put(6, SHMap.get(10));
                    SHMap.put(10, null);
                    return newList = SHMap;
                } else if (SHMap.get(11) == null) {
                    SHMap.put(11, SHMap.get(10));
                    SHMap.put(10, null);
                    return newList = SHMap;
                } else if (SHMap.get(9) == null) {
                    SHMap.put(9, SHMap.get(10));
                    SHMap.put(10, null);
                    return newList = SHMap;
                } else if (SHMap.get(14) == null) {
                    SHMap.put(14, SHMap.get(10));
                    SHMap.put(10, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 11: {
                if (SHMap.get(7) == null) {
                    SHMap.put(7, SHMap.get(11));
                    SHMap.put(11, null);
                    return newList = SHMap;
                } else if (SHMap.get(10) == null) {
                    SHMap.put(10, SHMap.get(11));
                    SHMap.put(11, null);
                    return newList = SHMap;
                } else if (SHMap.get(15) == null) {
                    SHMap.put(15, SHMap.get(11));
                    SHMap.put(11, null);
                    return newList = SHMap;
                }else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 12: {
                if (SHMap.get(8) == null) {
                    SHMap.put(8, SHMap.get(12));
                    SHMap.put(12, null);
                    return newList = SHMap;
                } else if (SHMap.get(13) == null) {
                    SHMap.put(13, SHMap.get(12));
                    SHMap.put(12, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 13: {
                if (SHMap.get(9) == null) {
                    SHMap.put(9, SHMap.get(13));
                    SHMap.put(13, null);
                    return newList = SHMap;
                } else if (SHMap.get(12) == null) {
                    SHMap.put(12, SHMap.get(13));
                    SHMap.put(13, null);
                    return newList = SHMap;
                }else if (SHMap.get(14) == null) {
                    SHMap.put(14, SHMap.get(13));
                    SHMap.put(13, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 14: {
                if (SHMap.get(13) == null) {
                    SHMap.put(13, SHMap.get(14));
                    SHMap.put(14, null);
                    return newList = SHMap;
                } else if (SHMap.get(10) == null) {
                    SHMap.put(10, SHMap.get(14));
                    SHMap.put(14, null);
                    return newList = SHMap;
                } else if (SHMap.get(15) == null) {
                    SHMap.put(15, SHMap.get(14));
                    SHMap.put(14, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
            case 15: {
                if (SHMap.get(11) == null) {
                    SHMap.put(11, SHMap.get(15));
                    SHMap.put(15, null);
                    return newList = SHMap;
                } else if (SHMap.get(14) == null) {
                    SHMap.put(14, SHMap.get(15));
                    SHMap.put(15, null);
                    return newList = SHMap;
                } else {
                    System.out.println("YOU ARE NOT ALLOW TO MAKE THIS MOVE");
                    return newList = SHMap;
                }
            }
        }

        return newList;
    }
}