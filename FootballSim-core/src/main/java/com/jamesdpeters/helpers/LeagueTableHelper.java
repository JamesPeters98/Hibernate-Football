package com.jamesdpeters.helpers;

import com.jamesdpeters.entities.FixtureResultEntity;
import com.jamesdpeters.entities.LeagueTableEntity;
import org.hibernate.Session;
import com.jamesdpeters.utils.SessionStore;
import com.jamesdpeters.utils.Utils;

import javax.persistence.NoResultException;
import java.util.*;

public class LeagueTableHelper {


    /**
     * Calculates the league table for a given season, league and gameweek.
     * Incremental, has to be called for every gameweek and only once per gameweek.
     * @param season
     * @param leagueID
     * @param gameweeks
     */
    public static void calculate(int season, int leagueID, List<Integer> gameweeks){
        System.err.println("Calculating league positions... League: "+leagueID);
        Session session = SessionStore.getSession();

        String query = "from FixtureResultEntity where fixture.seasonId = "+season+" and fixture.gameweek IN("+Utils.toCommaList(gameweeks)+") and fixture.leagueid = "+leagueID;
        List<FixtureResultEntity> results = session.createQuery(query, FixtureResultEntity.class).list();
        HashMap<Integer,LeagueTableEntity> leagueMap = getLeagueTableMap(season,leagueID);

        for(FixtureResultEntity result : results){
            LeagueTableEntity home = getTeamTableEntity(leagueMap,season, result.getFixture().getHometeamid(), leagueID);
            LeagueTableEntity away = getTeamTableEntity(leagueMap,season, result.getFixture().getAwayteamid(), leagueID);

            performResultAnalysis(result,home,away);
        }

        session.beginTransaction();
        leagueMap.values().forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        session.close();
    }

    public static void calculate(int season, int leagueID, int gameweek){
        calculate(season,leagueID, Collections.singletonList(gameweek));
    }

    private static LeagueTableEntity getTeamTableEntity(HashMap<Integer,LeagueTableEntity> map, int season, int teamId, int leagueId){
        //Check if team is already in the map
        LeagueTableEntity table = map.get(teamId);
        if(table != null) return table;

        //If not try and find it in the database or create a new entry.
        Session session = SessionStore.getSession();
        try {
            table = session.createQuery("from LeagueTableEntity where teamid = "+teamId+" and season = "+season+" and leagueId = "+leagueId, LeagueTableEntity.class).getSingleResult();
        } catch (NoResultException e){
            table = new LeagueTableEntity();
            table.setLeagueId(leagueId);
            table.setTeamid(teamId);
            table.setSeason(season);
        }

        map.put(teamId,table);
        session.close();
        return table;
    }

    public static List<LeagueTableEntity>  getLeagueTable(int season, int leagueId){
        Session session = SessionStore.getSession();
        List<LeagueTableEntity> table = session.createQuery("from LeagueTableEntity where season = "+season+" and leagueId = "+leagueId+" order by (3*wins+draws) desc, (goalsScored-goalsConceeded) desc, goalsScored desc , goalsConceeded asc ", LeagueTableEntity.class).list();
        session.close();
        return table;
    }

    public static HashMap<Integer, LeagueTableEntity> getLeagueTableMap(int season, int leagueId){
        HashMap<Integer, LeagueTableEntity> map = new HashMap<>();
        List<LeagueTableEntity> leagueTableEntities = getLeagueTable(season,leagueId);
        for(LeagueTableEntity table : leagueTableEntities){
            map.put(table.getTeamid(),table);
        }
        return map;
    }


    private static void performResultAnalysis(FixtureResultEntity result, LeagueTableEntity home, LeagueTableEntity away){
        if(result.getHomeGoals() > result.getAwayGoals()){
            home.addWin();
            away.addLoss();
        }

        if(result.getAwayGoals() > result.getHomeGoals()){
            home.addLoss();
            away.addWin();
        }

        if(result.getHomeGoals().equals(result.getAwayGoals())){
            home.addDraw();
            away.addDraw();
        }

        home.addGoalsScored(result.getHomeGoals());
        home.addGoalsScored(result.getAwayGoals());

        away.addGoalsScored(result.getAwayGoals());
        away.addGoalsConceeded(result.getHomeGoals());
    }
}
