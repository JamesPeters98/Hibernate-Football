package com.jamesdpeters.workers;

import com.jamesdpeters.entities.PlayerRatingsEntity;
import com.jamesdpeters.entities.PlayersEntity;
import com.jamesdpeters.entities.PositionsEntity;
import com.jamesdpeters.utils.PlayerRatingsUtil;
import com.jamesdpeters.utils.SessionStore;
import com.jamesdpeters.utils.Utils;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;

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

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new CalculatePlayerRatings();
    }

    public CalculatePlayerRatings() throws InterruptedException, ExecutionException {
        startTime = System.currentTimeMillis();

        session = SessionStore.getSession();

        executor = Executors.newFixedThreadPool(8);

        List<PositionsEntity> positionsEntities = session.createQuery("from PositionsEntity",PositionsEntity.class).list();
        List<PlayersEntity> players = session.createQuery("from PlayersEntity",PlayersEntity.class).list();

        //session.close();

        List<Callable<PlayerRatingsEntity>> callables = new ArrayList<>();

        //session.beginTransaction();
        System.out.println("Player base: "+players.size());
        for(PlayersEntity playersEntity : players){
            //Utils.logger.debug("Calculating ratings for "+playersEntity.getName());
            for(PositionsEntity positionsEntity : positionsEntities){

                Callable run = (Callable<PlayerRatingsEntity>) () -> {
                    n++;
                    PlayerRatingsEntity ratingsEntity = new PlayerRatingsEntity();
                    ratingsEntity.setId(playersEntity.getId());
                    ratingsEntity.setPositionId(positionsEntity.getId());

                    double rating = PlayerRatingsUtil.calculateOverallRating(playersEntity,positionsEntity);
                    double atkRating = PlayerRatingsUtil.calculateAttackRating(playersEntity,positionsEntity);
                    double defRating = PlayerRatingsUtil.calculateDefenceRating(playersEntity,positionsEntity);

                    ratingsEntity.setRating(rating);
                    ratingsEntity.setAttackrating(atkRating);
                    ratingsEntity.setDefencerating(defRating);

//                    session.beginTransaction();
//                    session.saveOrUpdate(ratingsEntity);
//                    session.getTransaction().commit();

                    if((n % 500) == 0) System.out.println(n);
                    //Utils.logger.debug("Ratings for "+playersEntity.getName()+" at pos: "+positionsEntity.getPosition()+" is A:"+atkRating+" D: "+defRating);

                    return ratingsEntity;
                };
                callables.add(run);
            }
        }

        List<Future<PlayerRatingsEntity>> results = executor.invokeAll(callables);

        System.out.println("Completed calculations: "+(System.currentTimeMillis()-startTime)/1000+"s");

//        session = SessionStore.getSession();
        session.clear();
        session.beginTransaction();
        for(Future<PlayerRatingsEntity> rating : results){
            //Utils.logger.debug(rating.get().getId());
            try {
                session.saveOrUpdate(rating.get());
            } catch(NoResultException e){
                e.printStackTrace();
                System.err.println("Rating: "+rating.get().getId());
            } catch (NonUniqueObjectException e){
                e.printStackTrace();
                System.err.println("Non Unique Player Rating: "+rating.get().getId());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        session.getTransaction().commit();
        session.close();
        executor.shutdown();

        endTime = System.currentTimeMillis();

        System.out.println("Time taken: "+(endTime-startTime)/1000+"s");
    }


}
