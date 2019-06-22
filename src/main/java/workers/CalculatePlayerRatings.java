package workers;

import entities.PlayerRatingsEntity;
import entities.PlayersEntity;
import entities.PositionsEntity;
import org.hibernate.Session;
import utils.SessionStore;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CalculatePlayerRatings {

    static long startTime;
    static long endTime;

    private static ExecutorService executor;

    final static int LIMIT = 50;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException, ExecutionException {
        startTime = System.currentTimeMillis();

        Session session = SessionStore.getSession();

        executor = Executors.newCachedThreadPool();

        List<PositionsEntity> positionsEntities = session.createQuery("from PositionsEntity",PositionsEntity.class).list();
        List<PlayersEntity> players = session.createQuery("from PlayersEntity where team.leagueid > 0",PlayersEntity.class).list();

        session.close();

        List<Callable<PlayerRatingsEntity>> callables = new ArrayList<>();

        Utils.logger.debug("Player base: "+players.size());
        for(PlayersEntity playersEntity : players){
            //Utils.logger.debug("Calculating ratings for "+playersEntity.getName());
            for(PositionsEntity positionsEntity : positionsEntities){

                Callable run = new Callable<PlayerRatingsEntity>() {
                    @Override
                    public PlayerRatingsEntity call() throws Exception {
                        PlayerRatingsEntity ratingsEntity = new PlayerRatingsEntity();
                        ratingsEntity.setId(playersEntity.getId());
                        ratingsEntity.setPositionId(positionsEntity.getId());

                        double rating = Utils.getPosRating(playersEntity,positionsEntity);
                        double atkRating = Utils.getAttackRating(playersEntity,positionsEntity);
                        double defRating = Utils.getDefenceRating(playersEntity,positionsEntity);

                        ratingsEntity.setRating(rating);
                        ratingsEntity.setAttackrating(atkRating);
                        ratingsEntity.setDefencerating(defRating);

                        Utils.logger.debug("Ratings for "+playersEntity.getName()+" at pos: "+positionsEntity.getPosition()+" is A:"+atkRating+" D: "+defRating);

                        return ratingsEntity;
                    }
                };
                callables.add(run);
            }
        }

        List<Future<PlayerRatingsEntity>> results = executor.invokeAll(callables);

        session = SessionStore.getSession();
        session.beginTransaction();
        for(Future<PlayerRatingsEntity> rating : results){
            Utils.logger.debug(rating.get().getId());
            session.saveOrUpdate(rating.get());
        }
        session.getTransaction().commit();
        session.close();

        endTime = System.currentTimeMillis();

        Utils.logger.debug("Time taken: "+(endTime-startTime)/1000+"s");
    }


}
