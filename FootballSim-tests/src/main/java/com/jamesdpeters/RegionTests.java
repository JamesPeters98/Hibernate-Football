package com.jamesdpeters;

import com.jamesdpeters.entities.LeaguesEntity;
import org.hibernate.Session;
import com.jamesdpeters.utils.SessionStore;

import java.util.List;

public class RegionTests {

    public static void main(String[] args) {
        SessionStore.setDB("GameSave");
        Session session = SessionStore.getSession();

        List<LeaguesEntity> leaguesEntities = session.createQuery("from LeaguesEntity where country.regionId = 0", LeaguesEntity.class).list();

        for(LeaguesEntity league : leaguesEntities){
            System.out.println(league.getLeaguename());
        }
    }
}
