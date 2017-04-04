package com.teamzenith.game.zpuzzle.model;


/**
 * Created by Hichem Memmi on 2017-03-09.
 */


/**
 * This Class is a Factory to create instances of Hard, Easy and Medium
 */
public class LevelFactory {
    private static LevelFactory levelFactory = null;

    private LevelFactory() {

    }

    /**
     * @return LevelFactory of type {@link LevelFactory}
     */
    public static LevelFactory getInstance() {
        if (levelFactory == null) {
            levelFactory = new LevelFactory();
        }
        return levelFactory;
    }

    /**
     *
     * @param levelType of type {@link LevelType}
     * @return Level of type {@link Level}
     */
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
                //System.out.println("false");
                return level;

        }
    }

}
