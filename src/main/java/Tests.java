import algorithms.BestTeamFormation;
import algorithms.BestTeamSheet;
import entities.PlayersEntity;
import entities.TeamsEntity;
import org.hibernate.Session;
import utils.SessionStore;
import utils.Utils;
import workers.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Tests {

    static long startTime;
    static long endTime;

    public static void main(String[] args) throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException {

        startTime = System.currentTimeMillis();

        Session session = SessionStore.getSession();
        List<TeamsEntity> teamsEntities = session.createQuery("from TeamsEntity where id < 100",TeamsEntity.class).list();

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

        List<Team> teams = new ArrayList<>();

        for(TeamsEntity teamsEntity : teamsEntities){
            Team team = new Team(session,teamsEntity);
            team.init();
            team.printInfo();
            //team.printFormation();
            teams.add(team);
        }



        endTime = System.currentTimeMillis();

        Utils.logger.debug("Time taken: "+(endTime-startTime)/1000+"s");

    }
}
