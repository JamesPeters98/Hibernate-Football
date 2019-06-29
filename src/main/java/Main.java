import Exceptions.NoDatabaseSelectedException;
import algorithms.BestTeamSheet;
import entities.*;
import org.hibernate.Session;
import utils.SessionStore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    static double teamRating;

    public static void main(final String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException, NoDatabaseSelectedException {
        final Session session = SessionStore.getSession();

        List<PlayersEntity> playersMain = session.createQuery("from PlayersEntity where team.leagueid = 1", PlayersEntity.class).list();
        List<PositionsEntity> positions = session.createQuery("from PositionsEntity", PositionsEntity.class).list();
        List<FormationsEntity> formations = session.createQuery("from FormationsEntity", FormationsEntity.class).list();

//        HashMap<Integer,PositionsEntity> posMap = new HashMap<>();
//
//        for(PositionsEntity pos : positions) {
//            posMap.put(pos.getId(), pos);
//        }


//        for(PlayersEntity player : playersMain){
//            double rating = getPosRating(player,Pos);
//            System.out.println(player.getName()+" has rating "+Math.round(rating)+" pot: "+player.getPotential());
//        }

//        double bestFormationRating = 0;
//        FormationsEntity bestFormation = null;
//        for(FormationsEntity formation : formations) {
//            System.out.println("Formation: "+formation.getFormation());
//            teamRating = 0;
//            //Best Goalkeeper
//            PlayersEntity goalkeeper = bestPlayerAtPos(posMap.get(11),playersMain);
//            playersMain.remove(goalkeeper);
//            System.out.println("Best Goalkeeper: "+goalkeeper.getName());
//
//            //Loop over all other players
//            List<PlayersEntity> players = new ArrayList<>(playersMain);
//            for (int posId : formation.getPositions()) {
//                PositionsEntity positionsEntity = posMap.get(posId);
//                PlayersEntity bestPlayer = bestPlayerAtPos(positionsEntity,players);
//                players.remove(bestPlayer);
//                System.out.println("Best player at: " + positionsEntity.getPosition() + " - " + bestPlayer.getName());
//            }
//            System.out.println("Formation: "+formation.getFormation()+" - "+teamRating/11);
//            if(teamRating > bestFormationRating){
//                bestFormationRating = teamRating;
//                bestFormation = formation;
//            }
//        }

//        double bestRating = 0;
//        FormationsEntity bestFormation = null;
//        for(FormationsEntity formation : formations){
//            List<PositionsEntity> pos = new ArrayList<>();
//            for (int i : formation.getPositions()){
//                pos.add(posMap.get(i));
//            }
//            System.out.println("Testing Formation: "+formation.getFormation());
//            BestTeamSheet bf = new BestTeamSheet(playersMain,pos,0.33);
//            if(bf.getRating() > bestRating){
//                bestRating = bf.getRating();
//                bestFormation = formation;
//            }
//        }
//
//        System.out.println("Best formation: "+bestFormation.getFormation());


//        TeamsEntity team = session.createQuery("from TeamsEntity where id = 1",TeamsEntity.class).getSingleResult();
//
//        System.out.println(team.getName());
//
//        for(PlayersEntity player : team.getPlayers()){
//            System.out.println(player.getName());
//        }


//        NationalityEntity nationalityEntity = session.createQuery("from NationalityEntity  where id = 1", NationalityEntity.class).getSingleResult();
//
//        Stream<PlayersEntity> b = nationalityEntity.getPlayers().stream().filter(playersEntity -> playersEntity.getOverall() > 80 );
//
//        b.forEach(playersEntity -> System.out.println(playersEntity.getStats().getAcceleration()));

//        System.out.println(nationalityEntity.getName());
//
//        for (PlayersEntity playersEntity : nationalityEntity.getPlayers()){
//            System.out.println(playersEntity.getName());
//        }

    }

}