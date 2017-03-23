package com.teamzenith.game.zpuzzle.model;


/**
 * Created by memmi on 2017-03-09.
 */

public class LevelFactory {
    private static LevelFactory levelFactory = null;

    private LevelFactory() {

    }

    /**
     *
     * @return
     */
    public static LevelFactory getInstance() {
        if (levelFactory == null) {
            levelFactory = new LevelFactory();
        }
        return levelFactory;
    }

    public Level createLevel(LevelType levelType) {
        Level level = null;

        switch (levelType) {
            case HARD: {
                level = new Hard();
                return level;
            }
            case EASY: {
                level = new Easy();
                return level;
            }
            case MEDIUM: {
                level = new Medium();
                return level;
            }
            default:
                System.out.println("false");
                return level;

        }
    }

}
