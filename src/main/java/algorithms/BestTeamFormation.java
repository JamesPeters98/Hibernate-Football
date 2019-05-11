package algorithms;

import entities.FormationsEntity;
import entities.PlayersEntity;
import entities.PositionsEntity;
import javafx.geometry.Pos;
import org.hibernate.Session;
import utils.DBUtil;
import utils.SessionStore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BestTeamFormation {

    private List<FormationsEntity> formations;
    private Session session;
    private BestTeamSheet bestTeamSheet = null;
    private ExecutorService executor;


    public BestTeamFormation(List<PlayersEntity> players, double potentialFactor) throws NoSuchFieldException, IllegalAccessException, InterruptedException, ExecutionException {
        session = SessionStore.getSession();
        formations = session.createQuery("from FormationsEntity", FormationsEntity.class).list();
        executor = Executors.newCachedThreadPool();

        List<Callable<BestTeamSheet>> bestTeamSheets = new ArrayList<>();

        double bestWeight = 0;

        //GK pos
        PositionsEntity GK = DBUtil.getPosition(11);

        for(FormationsEntity formation : formations){
            List<PositionsEntity> pos = DBUtil.getPositions(formation.getPositions());
            pos.add(GK);
            //System.out.println("Testing Formation: "+formation.getFormation());
            bestTeamSheets.add(new BestTeamSheet(formation,players,pos,potentialFactor));
        }
        List<Future<BestTeamSheet>> results = executor.invokeAll(bestTeamSheets);

        for(Future<BestTeamSheet> teamSheetFuture : results){
            BestTeamSheet teamSheet = teamSheetFuture.get();
            System.out.println("Formation: "+teamSheet.getFormation().getFormation()+" - Rated: "+teamSheet.getRating()+" - Weight: "+teamSheet.getWeight());
            if(teamSheet.getWeight() > bestWeight){
                bestWeight = teamSheet.getWeight();
                bestTeamSheet = teamSheet;
            }
        }

        executor.shutdown();

    }

    public BestTeamSheet getBestTeamSheet() {
        return bestTeamSheet;
    }
}
