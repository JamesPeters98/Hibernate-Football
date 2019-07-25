package com.jamesdpeters;

import com.jamesdpeters.exceptions.NoDatabaseSelectedException;
import com.jamesdpeters.entities.FixturesEntity;
import com.jamesdpeters.entities.LeaguesEntity;
import com.jamesdpeters.entities.TeamsEntity;
import com.jamesdpeters.frameworks.League;
import org.hibernate.Session;
import com.jamesdpeters.utils.SessionStore;
import com.jamesdpeters.utils.Utils;

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

        session.beginTransaction();
        session.createQuery("delete from FixturesEntity").executeUpdate();
        session.getTransaction().commit();

        List<LeaguesEntity> leaguesEntities = session.createQuery("from LeaguesEntity where id = 13", LeaguesEntity.class).list();

        System.out.println(leaguesEntities.size());

        List<League> leagues = new ArrayList<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        leaguesEntities.forEach(leaguesEntity -> {
            League league = new League(SessionStore.getSession(),leaguesEntity,1);
            leagues.add(league);
        });

        List<Future<League>> leagueFutures = null;
        try {
            leagueFutures = executorService.invokeAll(leagues);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        for(Future<League> leagueFuture : leagueFutures){
//            System.out.println("League: "+leagueFuture.get().getLeaguesEntity().getLeaguename());
//            leagueFuture.get().printFixtures();
//
//        }

        int teamId = leaguesEntities.get(0).getTeams().get(0).getId();

        TeamsEntity team = session.createQuery("from TeamsEntity where id = "+teamId,TeamsEntity.class).getSingleResult();
        List<FixturesEntity> fixturesEntities = session.createQuery("from FixturesEntity where hometeamid  = "+teamId+" or awayteamid = "+teamId+" order by gameweek asc ", FixturesEntity.class).list();
        System.out.println("Fixture list for: "+team.getName());
        for(FixturesEntity fixturesEntity : fixturesEntities){
            System.out.println(fixturesEntity.getGameweek()+": "+fixturesEntity.getHometeam().getName()+" vs "+fixturesEntity.getAwayteam().getName());
        }


        Utils.logger.info("Time taken: "+(System.currentTimeMillis()-start)+" ms");

    }
}
