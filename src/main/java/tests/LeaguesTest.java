package tests;

import Exceptions.NoDatabaseSelectedException;
import entities.LeaguesEntity;
import frameworks.League;
import org.hibernate.Session;
import utils.SessionStore;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LeaguesTest {

    public static void main(String[] args) throws NoDatabaseSelectedException, ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        SessionStore.setDB("TEST");
        Session session = SessionStore.getSession();
        List<LeaguesEntity> leaguesEntities = session.createQuery("from LeaguesEntity where id = 13", LeaguesEntity.class).list();

        System.out.println(leaguesEntities.size());

        List<League> leagues = new ArrayList<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        leaguesEntities.forEach(leaguesEntity -> {
            try {
                League league = new League(SessionStore.getSession(),leaguesEntity);
                leagues.add(league);
            } catch (NoDatabaseSelectedException e) {
                e.printStackTrace();
            }
        });

        List<Future<League>> leagueFutures = null;
        try {
            leagueFutures = executorService.invokeAll(leagues);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Future<League> leagueFuture : leagueFutures){
            System.out.println("League: "+leagueFuture.get().getLeaguesEntity().getLeaguename());
            leagueFuture.get().printFixtures();

        }

        Utils.logger.info("Time taken: "+(System.currentTimeMillis()-start)+" ms");

    }
}
