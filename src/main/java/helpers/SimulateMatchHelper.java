package helpers;

import entities.FixtureResultEntity;
import entities.FixturesEntity;
import entities.TeamsEntity;
import frameworks.Team;
import frameworks.VersusGame;
import org.hibernate.Session;
import utils.SessionStore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SimulateMatchHelper {

    public static void simulate(List<FixturesEntity> fixtures){
        List<FixtureResultEntity> results = new ArrayList<>();

        for(FixturesEntity fixture : fixtures) {
            Team home = new Team(SessionStore.getSession(), fixture.getHometeam());
            Team away = new Team(SessionStore.getSession(), fixture.getAwayteam());

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
        }

        Session session = SessionStore.getSession();
        session.beginTransaction();
        results.forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        session.close();
    }
}
