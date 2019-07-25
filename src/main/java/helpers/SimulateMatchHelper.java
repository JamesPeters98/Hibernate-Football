package helpers;

import entities.FixtureResultEntity;
import entities.FixturesEntity;
import entities.TeamsEntity;
import frameworks.Team;
import frameworks.VersusGame;
import listeners.ProgressListener;
import org.hibernate.Session;
import utils.SessionStore;

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
