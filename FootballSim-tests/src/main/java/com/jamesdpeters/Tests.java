package com.jamesdpeters;

import com.jamesdpeters.exceptions.NoDatabaseSelectedException;
import com.jamesdpeters.entities.TeamsEntity;
import com.jamesdpeters.frameworks.Team;
import org.hibernate.Session;
import com.jamesdpeters.utils.SessionStore;
import com.jamesdpeters.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Tests {

    static long startTime;
    static long endTime;
    static int n = 0;


    private static ExecutorService executor;

    public static void main(String[] args) throws InterruptedException, ExecutionException, NoSuchFieldException, IllegalAccessException, NoDatabaseSelectedException {

        startTime = System.currentTimeMillis();

        executor = Executors.newSingleThreadExecutor();

        SessionStore.setDB("TEST");
        Session session = SessionStore.getSession();
//        List<NationalityEntity> teamsEntities = session.createQuery("from NationalityEntity where id < 40 and id > 0",NationalityEntity.class).setMaxResults(200).list();
        List<TeamsEntity> teamsEntities = session.createQuery("from TeamsEntity where leagueid = 13",TeamsEntity.class).list();

        Utils.logger.debug("Teams: "+teamsEntities.size());

//        List<PlayersEntity> players = session.createQuery("from PlayersEntity where teamId = 1",PlayersEntity.class).list();
//
//        System.out.print(Utils.logger.getLevel().toString());
//        Utils.logger.info("Player base: "+players.size());
//        BestTeamFormation btf = new BestTeamFormation(players,0.3);
//        BestTeamSheet ts = btf.getBestTeamSheet();
//        Utils.logger.debug("Best Formation: "+ts.getFormation().getFormation()+" - Rated: "+ts.getRating()+" - Weight: "+ts.getWeight());
//
//
//        final double[] totalAttackWeight = {0};
//        final double[] totalDefenceWeight = {0};
//
//        ts.getPlayerPositions().forEach((playersEntity, positionsEntity) -> {
//            try {
//                double attackWeight = Utils.getAttackRating(playersEntity,positionsEntity);
//                double defenceWeight = Utils.getDefenceRating(playersEntity,positionsEntity);
//                totalAttackWeight[0] += attackWeight;
//                totalDefenceWeight[0] += defenceWeight;
//                Utils.logger.info(playersEntity.getName()+" at "+positionsEntity.getPosition()+" rated: "+ Utils.getPosRating(playersEntity,positionsEntity)+" pos type: "+positionsEntity.getPositiontype().getPosition()+" attack rating: "+attackWeight);
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        });
//
//        Utils.logger.info("Attacking Weight Average: "+totalAttackWeight[0]/11);
//        Utils.logger.info("Defence Weight Average: "+totalDefenceWeight[0]/11);

        List<Future<Team>> teams = new ArrayList<>();

        List<Callable<Team>> callables = new ArrayList<>();


//        Utils.logger.debug("Nationality: "+teamsEntities.get(0).getName());
//            NationalTeam team1 = new NationalTeam(session,teamsEntities.get(0));
//            team1.init();
//            team1.printInfo();
//            team1.printFormation();

        for(TeamsEntity teamsEntity : teamsEntities){
        //for(TeamsEntity teamsEntity : teamsEntities){
            //Utils.logger.debug("Adding "+teamsEntity.getName());
            Callable run = (Callable<Team>) () -> {
                //Utils.logger.debug("Creating team "+teamsEntity.getName());
//                NationalTeam team = new NationalTeam(session,nationalityEntity);
                Team team = new Team(session,teamsEntity);
                try{
                    team.init();
                } catch (Exception e){
                    e.printStackTrace();
                }
                //team.printInfo();
                //n++;
                //Utils.logger.debug(teamsEntity.getName()+" n: "+n);
                //team.printFormation();
                return team;
            };
            callables.add(run);
        }

        teams = executor.invokeAll(callables);

        Utils.logger.debug("teams final size "+teams.size());

        for(Future<Team> future : teams){
            future.get().printInfo();
        }

        endTime = System.currentTimeMillis();

        Utils.logger.debug("Time taken: "+(endTime-startTime)/1000+"s");

    }
}
