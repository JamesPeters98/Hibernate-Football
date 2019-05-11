package utils;

import entities.PositionsEntity;

import java.util.List;

public class DBUtil {

    public static List<PositionsEntity> getPositions(int... ids){
        return SessionStore.getSession().byMultipleIds(PositionsEntity.class).multiLoad(ids);
    }

    public static List<PositionsEntity> getPositions(List<Integer> ids){
        return SessionStore.getSession().byMultipleIds(PositionsEntity.class).multiLoad(ids);
    }

    public static PositionsEntity getPosition(int id){
        return SessionStore.getSession().byId(PositionsEntity.class).load(id);
    }

}
