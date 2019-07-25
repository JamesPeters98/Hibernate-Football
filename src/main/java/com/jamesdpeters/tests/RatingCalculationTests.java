package com.jamesdpeters.tests;

import com.jamesdpeters.Exceptions.NoDatabaseSelectedException;
import com.jamesdpeters.entities.PlayersEntity;
import com.jamesdpeters.entities.PositionsEntity;
import org.hibernate.Session;
import com.jamesdpeters.utils.PlayerRatingsUtil;
import com.jamesdpeters.utils.SessionStore;
import com.jamesdpeters.utils.Utils;

import java.lang.reflect.Field;

public class RatingCalculationTests {

    public static void main(String[] args) throws NoDatabaseSelectedException, NoSuchFieldException, IllegalAccessException {
        new RatingCalculationTests();
    }

    public RatingCalculationTests() throws NoDatabaseSelectedException, NoSuchFieldException, IllegalAccessException {
        SessionStore.setDB("TEST");
        Session session = SessionStore.getSession();
        PlayersEntity player = session.createQuery("from PlayersEntity where id = 209499", PlayersEntity.class).getSingleResult();
        PositionsEntity positionsEntity = session.createQuery("from PositionsEntity where id = 10", PositionsEntity.class).getSingleResult();

        for (Field field : player.getStats().getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Field posF = positionsEntity.getClass().getDeclaredField(field.getName());
            posF.setAccessible(true);
            Utils.logger.debug(field.getName()+":"+field.get(player.getStats())+" - "+posF.getName()+":"+posF.get(positionsEntity));
        }

        Utils.logger.info(PlayerRatingsUtil.calculatePosTypeRating(player,positionsEntity));
    }
}
