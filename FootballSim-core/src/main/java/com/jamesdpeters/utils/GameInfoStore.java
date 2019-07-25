package com.jamesdpeters.utils;

import com.jamesdpeters.entities.GameInfoEntity;
import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;

import javax.persistence.NoResultException;

public class GameInfoStore {

    private static GameInfoEntity gameInfoEntity;

    private static Session session;

    /**
     * @return true if the GameInfo existed, false if a new game was created.
     */
    public static boolean readGameInfo() {
        if((session == null) || (!session.isOpen())) session = SessionStore.getSession();
        try {
            session.clear();
            gameInfoEntity = session.createQuery("from GameInfoEntity", GameInfoEntity.class).getSingleResult();
            return true;
        } catch (NoResultException e){
            System.out.println("STARTING NEW GAME! WOO!");
            gameInfoEntity = new GameInfoEntity();
            gameInfoEntity.setId(1);
            gameInfoEntity.setGameStarted(false);
            session.beginTransaction();
            session.save(gameInfoEntity);
            session.getTransaction().commit();
            return false;
        } catch (SQLGrammarException e){
            SessionStore.resetDB();
            return false;
        }
    }

    public static GameInfoEntity getGameInfo() {
        return gameInfoEntity;
    }

    public static void updateGameInfo(GameInfoEntity gameInfoEntity){
        session.clear();
        session.beginTransaction();
        session.saveOrUpdate(gameInfoEntity);
        session.getTransaction().commit();
        readGameInfo();
    }

    public static void updateGameInfo(){
        updateGameInfo(GameInfoStore.getGameInfo());
    }
}
