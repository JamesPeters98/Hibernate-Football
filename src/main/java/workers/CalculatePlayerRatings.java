package workers;

import entities.PlayerRatingsEntity;
import entities.PlayersEntity;
import entities.PositionsEntity;
import org.hibernate.Session;
import utils.PlayerRatingsUtil;
import utils.SessionStore;
import utils.Utils;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CalculatePlayerRatings {

    static long startTime;
    static long endTime;
    static Session session;

    private static ExecutorService executor;

    final static int LIMIT = 50;

    static int n = 0;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException, ExecutionException {
        startTime = System.currentTimeMillis();

        SessionStore.setDB("TEST");
        session = SessionStore.getSession();

        executor = Executors.newFixedThreadPool(8);

        List<PositionsEntity> positionsEntities = session.createQuery("from PositionsEntity",PositionsEntity.class).list();
        List<PlayersEntity> players = session.createQuery("from PlayersEntity",PlayersEntity.class).list();

        //session.close();

        List<Callable<PlayerRatingsEntity>> callables = new ArrayList<>();

        //session.beginTransaction();
        Utils.logger.debug("Player base: "+players.size());
        for(PlayersEntity playersEntity : players){
            //Utils.logger.debug("Calculating ratings for "+playersEntity.getName());
            for(PositionsEntity positionsEntity : positionsEntities){

                Callable run = (Callable<PlayerRatingsEntity>) () -> {
                    n++;
                    PlayerRatingsEntity ratingsEntity = new PlayerRatingsEntity();
                    ratingsEntity.setId(playersEntity.getId());
                    ratingsEntity.setPositionId(positionsEntity.getId());

                    double rating = PlayerRatingsUtil.calculatePosRating(playersEntity,positionsEntity);
                    double atkRating = PlayerRatingsUtil.calculateAttackRating(playersEntity,positionsEntity);
                    double defRating = PlayerRatingsUtil.calculateDefenceRating(playersEntity,positionsEntity);

                    ratingsEntity.setRating(rating);
                    ratingsEntity.setAttackrating(atkRating);
                    ratingsEntity.setDefencerating(defRating);

//                    session.beginTransaction();
//                    session.saveOrUpdate(ratingsEntity);
//                    session.getTransaction().commit();

                    if((n % 500) == 0) Utils.logger.debug(n);
                    //Utils.logger.debug("Ratings for "+playersEntity.getName()+" at pos: "+positionsEntity.getPosition()+" is A:"+atkRating+" D: "+defRating);

                    return ratingsEntity;
                };
                callables.add(run);
            }
        }

        List<Future<PlayerRatingsEntity>> results = executor.invokeAll(callables);

        Utils.logger.debug("Completed calculations: "+(System.currentTimeMillis()-startTime)/1000+"s");

//        session = SessionStore.getSession();
        session.beginTransaction();
        for(Future<PlayerRatingsEntity> rating : results){
            //Utils.logger.debug(rating.get().getId());
            try {
                session.saveOrUpdate(rating.get());
            } catch(NoResultException e){
                e.printStackTrace();
                Utils.logger.error("Rating: "+rating.get().getId());
            }
        }
        session.getTransaction().commit();
        session.close();
        executor.shutdown();

        endTime = System.currentTimeMillis();

        Utils.logger.debug("Time taken: "+(endTime-startTime)/1000+"s");
    }


}
