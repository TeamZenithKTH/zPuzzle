package com.teamzenith.game.zpuzzle.model;

/**
 * Created by Hichem Memmi on 2017-03-09.
 */


/**
 * This class is a Game level that extends from Level
 */
public class Easy extends Level {

    public final int ROW = 3;
    public final int COLUMN = 3;
    private final int size=300;
    private final int sizeOfPiece=100;

    /**
     * @return String
     */
    public String toString() {
        return "Easy";
    }

    /**
     * get the size, which will used as a size of the game space
     * @return
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * get the size, which will used as a size of each piece of the puzzle
     * @return
     */
    @Override
    public int getSizeOfPiece() {
        return sizeOfPiece;
    }

    /**
     * get the row
     * @return
     */

    @Override
    public int getSizeOfRow() {
        return ROW;
    }

    /**
     * get column
     * @return
     */
    @Override
    public int getSizeOfColumn() {
        return COLUMN;
    }
}
