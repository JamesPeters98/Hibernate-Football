package com.jamesdpeters.utils;

import com.jamesdpeters.entities.PlayerStatsEntity;
import com.jamesdpeters.entities.PlayersEntity;
import com.jamesdpeters.entities.PositionsEntity;
import com.jamesdpeters.entities.PositiontypeEntity;

import java.lang.reflect.Field;
import java.util.List;

public class PlayerRatingsUtil {

    public static double calculatePosTypeRating(PlayersEntity player, PositionsEntity pos) throws NoSuchFieldException, IllegalAccessException {
        return calculatePosTypeRating(player,pos.getPositiontype());
    }

    public static double calculatePosTypeRating(PlayersEntity player, PositiontypeEntity pos) throws NoSuchFieldException, IllegalAccessException {
        PlayerStatsEntity stats = player.getStats();
        //Utils.logger.debug(player.getName()+":"+pos.getId());
        return getOverall(stats,pos);
    }

    public static double calculateOverallRating(PlayersEntity player, PositionsEntity pos) throws NoSuchFieldException, IllegalAccessException {
        PlayerStatsEntity stats = player.getStats();
        return getOverall(stats,pos);
    }

    public static double calculateAttackRating(PlayersEntity player, PositionsEntity pos) throws NoSuchFieldException, IllegalAccessException {
        DBUtil db = new DBUtil();
        List<PositiontypeEntity> posTypes = db.getPositiontypes();
        double attackRating = 0;

        for(PositiontypeEntity posType : posTypes){
            double attackWeight = db.getPositionAttackWeight(pos.getId(),posType.getId());
            attackRating += calculatePosTypeRating(player,posType)*attackWeight;
        }
        return attackRating;
    }

    public static double calculateDefenceRating(PlayersEntity player, PositionsEntity pos) throws NoSuchFieldException, IllegalAccessException {
        DBUtil db = new DBUtil();
        List<PositiontypeEntity> posTypes = db.getPositiontypes();
        double defenceRating = 0;

        for(PositiontypeEntity posType : posTypes){
            double attackWeight = db.getPositionDefenceWeight(pos.getId(),posType.getId());
            defenceRating += calculatePosTypeRating(player,posType)*attackWeight;
        }
        return defenceRating;
    }

    private static double getOverall(PlayerStatsEntity stats, Object pos) throws NoSuchFieldException, IllegalAccessException {
        double overall = 0;
        for(Field field : stats.getClass().getDeclaredFields()){
            if(field.getName().equals("id")) continue;

            field.setAccessible(true);
            Field posF = pos.getClass().getDeclaredField(field.getName());
            posF.setAccessible(true);

            int stat = 0;
            double coef = 0;

            Object coefO = posF.get(pos);
            Object statO = field.get(stats);

            if(coefO != null) coef = (double) coefO;
            if(statO != null) stat = (int) statO;

            //if(coef != 0) Utils.logger.debug(field.getName()+": "+stat+"*"+coef);

            overall += stat*coef;
        }
        return overall;
    }
}
