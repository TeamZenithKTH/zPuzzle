package com.teamzenith.game.zpuzzle.model;

import java.io.Serializable;

/**
 * Created by Hichem Memmi on 2017-03-09.
 */

/**
 * This is an abstract method that implements Serializable
 */
public abstract class Level implements Serializable {

    /**
     *
     */
    public Level() {

    }

    public abstract int getSize();

    public abstract int getSizeOfPiece();

    public abstract int getSizeOfRow();

    public abstract int getSizeOfColumn();

    /**
     * Get the level name.
     * @return
     */
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
