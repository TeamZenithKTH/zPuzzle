package com.teamzenith.game.zpuzzle.model;


/**
 * Created by Hichem Memmi on 2017-03-09.
 */


/**
 * This Class is a Factory to create instances of Hard, Easy and Medium
 */
public class LevelFactory {
    private static LevelFactory niveauFactory = null;

    private LevelFactory() {

    }

    /**
     * @return LevelFactory of type {@link LevelFactory}
     */
    public static LevelFactory getInstance() {
        if (niveauFactory == null) {
            niveauFactory = new LevelFactory();
        }
        return niveauFactory;
    }

    /**
     *
     * @param niveauType of type {@link LevelType}
     * @return Level of type {@link Level}
     */
    public Level createNiveau(LevelType niveauType) {
        Level niveau = null;

        switch (niveauType) {
            case HARD: {
                niveau = new Hard();
                return niveau;
            }
            case EASY: {
                niveau = new Easy();
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
