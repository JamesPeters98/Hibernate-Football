package utils;

import entities.*;
import org.hibernate.Session;

import java.lang.reflect.Field;
import java.util.List;

public class PlayerRatingsUtil {

    private Session session;

//    public PlayerRatingsUtil(){
//        session = SessionStore.getSession();
//    }
//
//    public void closeSession(){
//        session.close();
//    }

    public static double calculatePosRating(PlayersEntity player, PositionsEntity pos) throws NoSuchFieldException, IllegalAccessException {
        return calculatePosRating(player,pos.getPositiontype());
    }

    public static double calculatePosRating(PlayersEntity player, PositiontypeEntity pos) throws NoSuchFieldException, IllegalAccessException {
        PlayerStatsEntity stats = player.getStats();

        double overall = 0;
        for(Field field : stats.getClass().getDeclaredFields()){
            if(field.getName() == "id") continue;

            field.setAccessible(true);
            Field posF = pos.getClass().getDeclaredField(field.getName());
            posF.setAccessible(true);

            int stat = 0;
            double coef = 0;

            Object coefO = posF.get(pos);
            Object statO = field.get(stats);

            if(coefO != null) coef = (double) coefO;
            if(statO != null) stat = (int) statO;

            overall += stat*coef;
        }

        return overall;
    }

    public static double calculateAttackRating(PlayersEntity player, PositionsEntity pos) throws NoSuchFieldException, IllegalAccessException {

        DBUtil db = new DBUtil();

        List<PositiontypeEntity> posTypes = db.getPositiontypes();

        double attackRating = 0;

        for(PositiontypeEntity posType : posTypes){
            double attackWeight = db.getPositionAttackWeight(pos.getId(),posType.getId());
            attackRating += calculatePosRating(player,posType)*attackWeight;
        }
        return attackRating;
    }

    public static double calculateDefenceRating(PlayersEntity player, PositionsEntity pos) throws NoSuchFieldException, IllegalAccessException {
        DBUtil db = new DBUtil();

        List<PositiontypeEntity> posTypes = db.getPositiontypes();

        double defenceRating = 0;

        for(PositiontypeEntity posType : posTypes){
            double attackWeight = db.getPositionDefenceWeight(pos.getId(),posType.getId());
            defenceRating += calculatePosRating(player,posType)*attackWeight;
        }
        return defenceRating;
    }

//    //Returns cached Attack Rating.
//    public double getAttackRating(PlayersEntity player, PositionsEntity pos){
//        try {
//            return session.createQuery("from PlayerRatingsEntity where id = "+player.getId()+" and positionId = "+pos.getId(), PlayerRatingsEntity.class).getSingleResult().getAttackrating();
//        } finally {
//            session.clear();
//        }
//    }
//
//    //Returns cached Defence Rating.
//    public double getDefenceRating(PlayersEntity player, PositionsEntity pos){
//        try {
//            return session.createQuery("from PlayerRatingsEntity where id = "+player.getId()+" and positionId = "+pos.getId(), PlayerRatingsEntity.class).getSingleResult().getDefencerating();
//        } finally {
//            session.clear();
//        }
//    }
//
//    //Returns cached Defence Rating.
//    public double getPosRating(PlayersEntity player, PositionsEntity pos){
//        try {
//            return session.createQuery("from PlayerRatingsEntity where id = "+player.getId()+" and positionId = "+pos.getId(), PlayerRatingsEntity.class).getSingleResult().getRating();
//        } finally {
//            session.clear();
//        }
//    }

}
