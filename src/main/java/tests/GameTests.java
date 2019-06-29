package tests;

import Exceptions.NoDatabaseSelectedException;
import entities.TeamsEntity;
import frameworks.Team;
import frameworks.VersusGame;
import org.hibernate.Session;
import utils.SessionStore;
import utils.Utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class GameTests {

    static long startTime;

    public static void main(String[] args) throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException, NoDatabaseSelectedException {
        startTime = System.currentTimeMillis();
        SessionStore.setDB("TEST");
        Session session = SessionStore.getSession();

        int team1id = 9;
        int team2id = 10;

        TeamsEntity team1ent = session.createQuery("from TeamsEntity where id = "+team1id,TeamsEntity.class).getSingleResult();
        TeamsEntity team2ent = session.createQuery("from TeamsEntity where id = "+team2id,TeamsEntity.class).getSingleResult();

        Team team1 = new Team(session,team1ent);
        Team team2 = new Team(session,team2ent);

        team1.init();
        team2.init();

        VersusGame game = new VersusGame(team1,team2);

        game.printInfo();

        double totalHome = 0;
        double totalAway = 0;

        double LIMIT = 500;
        for(int i = 0; i <= LIMIT; i++){
            int homeGoals = Utils.poisson(game.getHomeGoals());
            int awayGoals = Utils.poisson(game.getAwayGoals());

            totalHome += homeGoals;
            totalAway += awayGoals;

            Utils.logger.debug(game.getHome().getTeamName()+" "+homeGoals+" - "+awayGoals+" "+game.getAway().getTeamName());
        }

        totalHome /= LIMIT;
        totalAway /= LIMIT;

        Utils.logger.debug(game.getHome().getTeamName()+" "+totalHome+" - "+totalAway+" "+game.getAway().getTeamName());



    }
}
