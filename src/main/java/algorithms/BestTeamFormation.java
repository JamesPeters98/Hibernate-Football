package algorithms;

import entities.FormationsEntity;
import entities.PlayersEntity;
import entities.PositionsEntity;
import javafx.geometry.Pos;
import org.hibernate.Session;
import utils.DBUtil;
import utils.SessionStore;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BestTeamFormation {

    private List<FormationsEntity> formations;
    private Session session;
    private BestTeamSheet bestTeamSheet = null;
    private ExecutorService executor;
    private DBUtil db;


    public BestTeamFormation(List<PlayersEntity> players, double potentialFactor) throws NoSuchFieldException, IllegalAccessException, InterruptedException, ExecutionException {
        session = SessionStore.getSession();
        formations = session.createQuery("from FormationsEntity", FormationsEntity.class).list();
        session.close();

        executor = Executors.newCachedThreadPool();
        db = new DBUtil();

        List<Callable<BestTeamSheet>> bestTeamSheets = new ArrayList<>();

        double bestWeight = 0;

        //GK pos
        PositionsEntity GK = db.getPosition(11);

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

    }

    public BestTeamSheet getBestTeamSheet() {
        return bestTeamSheet;
    }
}
