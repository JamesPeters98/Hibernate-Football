package com.jamesdpeters.helpers;

import com.jamesdpeters.entities.FixtureResultEntity;
import com.jamesdpeters.entities.FixturesEntity;
import com.jamesdpeters.frameworks.Team;
import com.jamesdpeters.frameworks.VersusGame;
import com.jamesdpeters.listeners.ProgressListener;
import com.jamesdpeters.utils.SessionStore;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SimulateMatchHelper {

    public static void simulate(List<FixturesEntity> fixtures, List<ProgressListener> progressListeners){
        List<FixtureResultEntity> results = new ArrayList<>();

        Session session = SessionStore.getSession();

        for(FixturesEntity fixture : fixtures) {
            Team home = new Team(session, fixture.getHometeam());
            Team away = new Team(session, fixture.getAwayteam());

            try {
                home.init();
                away.init();
            } catch (InterruptedException | ExecutionException | NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            VersusGame game = new VersusGame(home, away);

            FixtureResultEntity result = new FixtureResultEntity();
            result.setFixtureId(fixture.getId());
            result.setHomeGoals(game.generateHomeGoals());
            result.setAwayGoals(game.generateAwayGoals());

            results.add(result);

            for(ProgressListener progressListener : progressListeners){
                progressListener.progress(1);
            }
        }

        session.beginTransaction();
        results.forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        session.close();
    }
}
