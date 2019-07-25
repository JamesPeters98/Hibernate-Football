package com.jamesdpeters.algorithms;

import com.jamesdpeters.entities.FormationsEntity;
import com.jamesdpeters.entities.PlayersEntity;
import com.jamesdpeters.entities.PositionsEntity;
import org.hibernate.Session;
import com.jamesdpeters.utils.DBUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BestTeamFormation {

    private List<FormationsEntity> formations;
    private Session session;
    private BestTeamSheet bestTeamSheet = null;
    private ExecutorService executor;
    private DBUtil db;

    private long startTime;
    private long endTime;

    public BestTeamFormation(Session session, List<PlayersEntity> players, double potentialFactor) throws NoSuchFieldException, IllegalAccessException, InterruptedException, ExecutionException {
//        startTime = System.currentTimeMillis();
        formations = session.createQuery("from FormationsEntity", FormationsEntity.class).list();
        session.clear();

        executor = Executors.newCachedThreadPool();
        db = new DBUtil();

        List<Callable<BestTeamSheet>> bestTeamSheets = new ArrayList<>();

        double bestWeight = 0;

        //GK pos
        PositionsEntity GK = db.getPosition("GK");

        for(FormationsEntity formation : formations){
            List<PositionsEntity> pos = db.getPositions(formation.getPositions());
            pos.add(GK);
            //Utils.logger.info("Testing Formation: "+formation.getFormation());
            bestTeamSheets.add(new BestTeamSheet(formation,players,pos,potentialFactor));
        }
        List<Future<BestTeamSheet>> results = executor.invokeAll(bestTeamSheets);

        for(Future<BestTeamSheet> teamSheetFuture : results){
            BestTeamSheet teamSheet = teamSheetFuture.get();
            //Utils.logger.info("Formation: "+teamSheet.getFormation().getFormation()+" - Rated: "+teamSheet.getRating()+" - Weight: "+teamSheet.getWeight());
            if(teamSheet.getWeight() > bestWeight){
                bestWeight = teamSheet.getWeight();
                bestTeamSheet = teamSheet;
            }
        }

        db.closeConnection();
        executor.shutdown();

//        endTime = System.currentTimeMillis();
//        Utils.logger.debug("Best Team Formation Time: "+(endTime-startTime)+"ms");

    }

    public BestTeamSheet getBestTeamSheet() {
        return bestTeamSheet;
    }
}
