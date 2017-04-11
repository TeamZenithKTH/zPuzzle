package com.teamzenith.game.zpuzzle.model;

import java.io.Serializable;

/**
 * Created by Hichem Memmi on 2017-03-09.
 */

/**
 * This is an abstract class which implements Serializable to be able to send it to an
 * other activity and inherited by {@link Easy}, {@link Medium} and {@link Hard}
 */
public abstract class Level implements Serializable {

    /**
     *Default constructor
     */
    public Level() {

    }

    /**
     * A method which will be implemented by child classes. and it is to get the size of the game space
     * @return
     */
    public abstract int getSize();

    /**
     * A method which will be implemented by child classes, and it is to get the size of each piece of the
     * puzzle
     * @return
     */
    public abstract int getSizeOfPiece();

    /**
     * A method which will be implemented by child classes, and it is to get the number of row
     * @return
     */
    public abstract int getSizeOfRow();

    /**
     * A method which will be implemented by child classes, and it is to get the number of column
     * @return
     */
    public abstract int getSizeOfColumn();

    /**
     * Get the level name.
     * @return
     */
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
