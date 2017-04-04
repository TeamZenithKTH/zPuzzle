package com.teamzenith.game.zpuzzle.model;

/**
 * Created by Hichem Memmi on 2017-03-09.
 */


/**
 * This class is a Game level that extends from {@link Level}
 */
public class Medium extends Level {

    public final int ROW = 4;
    public  final int COLUMN = 4;

    private final int size=344;
    private final int sizeOfPiece=86;

    /**
     *
     * @return String
     */
    public String toString() {
        return "Medium";
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
