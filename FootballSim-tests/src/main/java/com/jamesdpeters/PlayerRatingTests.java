package com.jamesdpeters;

import com.jamesdpeters.exceptions.NoDatabaseSelectedException;
import com.jamesdpeters.entities.PlayerRatingsEntity;
import org.hibernate.Session;
import com.jamesdpeters.utils.SessionStore;
import com.jamesdpeters.utils.Utils;

import java.util.concurrent.ExecutionException;

public class PlayerRatingTests {

    static long startTime;
    static long endTime;

    public static void main(String[] args) throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException, NoDatabaseSelectedException {

        startTime = System.currentTimeMillis();

        Session session = SessionStore.getSession();
        //PlayersEntity players = session.createQuery("from PlayersEntity where id = 1",PlayersEntity.class).getSingleResult();

        //Utils.logger.debug(players.getPositionRatings().p);

        PlayerRatingsEntity rating = new PlayerRatingsEntity();
        rating.setId(1);
        rating.setPositionId(3);
        rating.setRating(67.0);

        session.beginTransaction();
        session.save(rating);
        session.getTransaction().commit();
        session.close();



        endTime = System.currentTimeMillis();

        Utils.logger.debug("Time taken: "+(endTime-startTime)/1000+"s");

    }
}
