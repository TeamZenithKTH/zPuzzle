package com.teamzenith.game.zpuzzle.model;

import java.io.Serializable;

/**
 * Created by memmi on 2017-03-09.
 */

public abstract class Level implements Serializable {

    protected int i;

    /**
     *
     */
    public Level(){

    }
   abstract protected void test();

}
