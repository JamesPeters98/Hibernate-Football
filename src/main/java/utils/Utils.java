package utils;

import entities.PlayerStatsEntity;
import entities.PlayersEntity;
import entities.PositionsEntity;
import entities.PositiontypeEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.lang.reflect.Field;
import java.util.List;

public class Utils {

    static {
        setLoggerLevel(Level.DEBUG);
    }

    public static Logger logger = LogManager.getLogger(Utils.class);

    public static void setLoggerLevel(Level level){
        Configurator.setLevel(Utils.class.getCanonicalName(),level);
    }

    public static double getPosRating(PlayersEntity player, PositionsEntity pos) throws NoSuchFieldException, IllegalAccessException {
        return getPosRating(player,pos.getPositiontype());
    }

    public static double getPosRating(PlayersEntity player, PositiontypeEntity pos) throws NoSuchFieldException, IllegalAccessException {
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

    public static double getAttackRating(PlayersEntity player, PositionsEntity pos) throws NoSuchFieldException, IllegalAccessException {
        DBUtil db = new DBUtil();

        List<PositiontypeEntity> posTypes = db.getPositiontypes();

        double attackRating = 0;

        for(PositiontypeEntity posType : posTypes){
            double attackWeight = db.getPositionAttackWeight(pos.getId(),posType.getId());
            attackRating += getPosRating(player,posType)*attackWeight;
        }

        db.closeConnection();
        return attackRating;
    }

    public static double getDefenceRating(PlayersEntity player, PositionsEntity pos) throws NoSuchFieldException, IllegalAccessException {
        DBUtil db = new DBUtil();

        List<PositiontypeEntity> posTypes = db.getPositiontypes();

        double defenceRating = 0;

        for(PositiontypeEntity posType : posTypes){
            double attackWeight = db.getPositionDefenceWeight(pos.getId(),posType.getId());
            defenceRating += getPosRating(player,posType)*attackWeight;
        }
        db.closeConnection();
        return defenceRating;
    }
}
