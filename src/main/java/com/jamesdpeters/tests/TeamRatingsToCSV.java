package com.jamesdpeters.tests;

import com.jamesdpeters.Exceptions.NoDatabaseSelectedException;
import com.jamesdpeters.entities.TeamsEntity;
import com.jamesdpeters.frameworks.Team;
import org.hibernate.Session;
import com.jamesdpeters.utils.CSVWriter;
import com.jamesdpeters.utils.SessionStore;
import com.jamesdpeters.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TeamRatingsToCSV {


    static long startTime;
    static long endTime;
    static int n = 0;


    private static ExecutorService executor;

    public static void main(String[] args) throws InterruptedException, ExecutionException, NoSuchFieldException, IllegalAccessException, NoDatabaseSelectedException, IOException {
        startTime = System.currentTimeMillis();
        executor = Executors.newSingleThreadExecutor();

        SessionStore.setDB("TEST");
        Session session = SessionStore.getSession();
        List<TeamsEntity> teamsEntities = session.createQuery("from TeamsEntity where leagueid IN (13,16,19,31,53)",TeamsEntity.class).list();

        Utils.logger.debug("Teams: "+teamsEntities.size());
        List<Future<Team>> teams;
        List<Callable<Team>> callables = new ArrayList<>();

        for(TeamsEntity teamsEntity : teamsEntities){
            Callable run = (Callable<Team>) () -> {
                Team team = new Team(session,teamsEntity);
                try{
                    team.init();
                } catch (Exception e){
                    e.printStackTrace();
                }
                return team;
            };
            callables.add(run);
        }

        teams = executor.invokeAll(callables);
        Utils.logger.debug("teams final size "+teams.size());

        CSVWriter csv = new CSVWriter("TeamRatings");
        csv.setHeader("Team","Rating","Attack Rating","Defence Rating");
        for(Future<Team> future : teams){
            Team team = future.get();
            csv.addRow(team.getTeamName(),team.getTeamRating(),team.getAttackRating(),team.getDefenceRating());
        }
        csv.write();

        session.close();
        executor.shutdown();

        endTime = System.currentTimeMillis();
        Utils.logger.debug("Time taken: "+(endTime-startTime)/1000+"s");

    }
}
