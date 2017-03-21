package com.teamzenith.game.zpuzzle.model;


/**
 * Created by memmi on 2017-03-09.
 */

public class LevelFactory {
    private static LevelFactory niveauFactory = null;

    private LevelFactory() {

    }

    /**
     *
     * @return
     */
    public static LevelFactory getInstance() {
        if (niveauFactory == null) {
            niveauFactory = new LevelFactory();
        }
        return niveauFactory;
    }

    public Level createNiveau(LevelType niveauType) {
        Level niveau = null;

        switch (niveauType) {
            case HARD: {
                niveau = new Hard();
                return niveau;
            }
            case KIDS: {
                niveau = new Kids();
                return niveau;
            }
            case MEDIUM: {
                niveau = new Medium();
                return niveau;
            }
            default:
                System.out.println("false");
                return niveau;

        }
    }

}
