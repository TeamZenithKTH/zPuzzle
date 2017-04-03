package com.teamzenith.game.zpuzzle.model;

/**
 * Created by Hichem Memmi on 2017-03-09.
 */


/**
 * This class is a Game level that extends from Level
 */
public class Hard extends Level {


    public final int ROW = 5;
    public final int COLUMN = 5;

    private final int size=350;
    private final int sizeOfPiece=70;

    /**
     * @return
     */
    public String toString() {
        return "hard";
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getSizeOfPiece() {
        return sizeOfPiece;
    }

    @Override
    public int getSizeOfRow() {
        return ROW;
    }

    @Override
    public int getSizeOfColumn() {
        return COLUMN;
    }
}
