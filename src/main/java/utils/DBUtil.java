package utils;

import Exceptions.NoDatabaseSelectedException;
import entities.*;
import org.hibernate.Session;

import java.util.List;

public class DBUtil {

    private Session session;

    public DBUtil() {
        createSession();
    }

    private void createSession() {
        session = SessionStore.getSession();
    }

    public void closeConnection(){
        session.close();
    }

    public List<PositionsEntity> getPositions(int... ids){
        try{
//            createSession();
            return session.byMultipleIds(PositionsEntity.class).multiLoad(ids);
        } finally {
            session.clear();
//            closeConnection();
        }
    }

    public List<PositionsEntity> getPositions(List<Integer> ids){
        try {
//            createSession();
            return session.byMultipleIds(PositionsEntity.class).multiLoad(ids);
        } finally {
            session.clear();
//            closeConnection();
        }

    }

    public PositionsEntity getPosition(int id){
        try {
//            createSession();
            return session.byId(PositionsEntity.class).load(id);
        } finally {
            session.clear();
//        closeConnection();
    }
    }

    public PositionsEntity getPosition(String name){
        try {
            name = "\'"+name+"\'";
            return session.createQuery("from PositionsEntity where position = "+name+" ",PositionsEntity.class).getSingleResult();
        } finally {
            session.clear();
        }
    }

    public List<PositiontypeEntity> getPositiontypes(){
        try {
//            createSession();
            return session.createQuery("from PositiontypeEntity", PositiontypeEntity.class).list();
        } finally {
            session.clear();
//            closeConnection();
        }
    }

    //Returns attacking weight for player based on their playing position in a certain positionType
    public double getPositionAttackWeight(int positionId, int positionType){
        try {
//            createSession();
            AttackWeightsEntity weightsEntity = session.createQuery("from AttackWeightsEntity where id = "+positionId+" and positiontype ="+positionType,AttackWeightsEntity.class).getSingleResult();
            return weightsEntity.getWeight();
        } finally {
            session.clear();
//            closeConnection();
        }
    }

    //Returns defensive weight for player based on their playing position in a certain positionType
    public double getPositionDefenceWeight(int positionId, int positionType){
        try {
//            createSession();
            DefenceWeightsEntity weightsEntity = session.createQuery("from DefenceWeightsEntity where id = "+positionId+" and positiontype ="+positionType, DefenceWeightsEntity.class).getSingleResult();
            return weightsEntity.getWeight();
        } finally {
            session.clear();
//            closeConnection();
        }
    }

    public List<LeaguesEntity> getLeagues(List<Integer> leagueIds){
        return session.createQuery("from LeaguesEntity where id in("+Utils.toCommaList(leagueIds)+")",LeaguesEntity.class).list();
    }

}
