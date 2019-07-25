package com.jamesdpeters;

import com.jamesdpeters.exceptions.NoDatabaseSelectedException;
import com.jamesdpeters.entities.FormationsEntity;
import com.jamesdpeters.entities.PositionsEntity;
import org.hibernate.Session;
import com.jamesdpeters.utils.DBUtil;
import com.jamesdpeters.utils.SessionStore;

import java.util.List;

public class PosTests {

    public static void main(String[] args) throws NoDatabaseSelectedException {

        SessionStore.setDB("TEST");
        Session session = SessionStore.getSession();

//        List<PlayersEntity> players = session.createQuery("from PlayersEntity where teamid = 9", PlayersEntity.class).list();
//
//        players.forEach((playersEntity -> {
//            System.out.println(playersEntity.getName()+": "+playersEntity.getPosition().getPosition());
//        }));

        List<FormationsEntity> formations = session.createQuery("from FormationsEntity", FormationsEntity.class).list();

        DBUtil db = new DBUtil();
        formations.forEach((formationsEntity -> {
            List<PositionsEntity> pos = db.getPositions(formationsEntity.getPositions());
            System.out.println(formationsEntity.getFormation());
            pos.forEach(positionsEntity -> System.out.println(positionsEntity.getId()));
        }));
    }
}
