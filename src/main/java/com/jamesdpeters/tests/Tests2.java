package com.jamesdpeters.tests;

import com.jamesdpeters.Exceptions.NoDatabaseSelectedException;
import com.jamesdpeters.entities.PlayersEntity;
import com.jamesdpeters.entities.PositionsEntity;
import org.hibernate.Session;
import com.jamesdpeters.utils.SessionStore;
import com.jamesdpeters.utils.Utils;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Tests2 {

    static long startTime;
    static long endTime;

    public static void main(String[] args) throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException, NoDatabaseSelectedException {

        startTime = System.currentTimeMillis();

        Session session = SessionStore.getSession();
        List<PlayersEntity> players = session.createQuery("from PlayersEntity where team.leagueid = 1",PlayersEntity.class).list();
        List<PositionsEntity> positions = session.createQuery("from PositionsEntity",PositionsEntity.class).list();

        //PlayerRatingsUtil prUtil = new PlayerRatingsUtil();

        for(PlayersEntity player : players){
            for (PositionsEntity pos : positions){
//                double atkRating = prUtil.getAttackRating(player,pos);
//                double defRating = prUtil.getDefenceRating(player,pos);
                double atkRating = player.getRating(pos.getId()).getAttackrating();
                double defRating = player.getRating(pos.getId()).getDefencerating();
                Utils.logger.debug("Player: "+player.getName()+" A: "+atkRating+" D: "+defRating);
            }
        }


//        List<PlayersEntity> players = session.createQuery("from PlayersEntity where teamId = 1",PlayersEntity.class).list();
//
//        System.out.print(Utils.logger.getLevel().toString());
//        Utils.logger.info("Player base: "+players.size());
//        BestTeamFormation btf = new BestTeamFormation(session,players,0.3);
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



        endTime = System.currentTimeMillis();

        Utils.logger.debug("Time taken: "+(endTime-startTime)/1000+"s");

    }
}
