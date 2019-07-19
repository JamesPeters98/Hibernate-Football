package helpers;

import entities.FixtureResultEntity;
import entities.LeagueTableEntity;
import org.hibernate.Session;
import utils.SessionStore;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class LeagueTableHelper {

    public static void calculate(int season, int leagueID, int gameweek){
        System.err.println("Calculating league positions... League: "+leagueID);
        Session session = SessionStore.getSession();

        List<FixtureResultEntity> results = session.createQuery("from FixtureResultEntity where fixture.seasonId = "+season+" and fixture.gameweek = "+gameweek+" and fixture.leagueid = "+leagueID, FixtureResultEntity.class).list();
        List<LeagueTableEntity> tableEntities = new ArrayList<>();

        for(FixtureResultEntity result : results){
            LeagueTableEntity home = getTeamTableEntity(season, result.getFixture().getHometeamid(), leagueID);
            LeagueTableEntity away = getTeamTableEntity(season, result.getFixture().getAwayteamid(), leagueID);

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

            tableEntities.add(home);
            tableEntities.add(away);
        }

        session.beginTransaction();
        tableEntities.forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        session.close();
    }

    private static LeagueTableEntity getTeamTableEntity(int season, int teamId, int leagueId){
        Session session = SessionStore.getSession();
        LeagueTableEntity table;
        try {
            table = session.createQuery("from LeagueTableEntity where teamid = "+teamId+" and season = "+season+" and leagueId = "+leagueId, LeagueTableEntity.class).getSingleResult();
        } catch (NoResultException e){
            table = new LeagueTableEntity();
            table.setLeagueId(leagueId);
            table.setTeamid(teamId);
            table.setSeason(season);
        }
        session.close();
        return table;
    }

    public static List<LeagueTableEntity>  getLeagueTable(int season, int leagueId){
        return SessionStore.getSession().createQuery("from LeagueTableEntity where season = "+season+" and leagueId = "+leagueId+" order by (3*wins+draws) desc, (goalsScored-goalsConceeded) desc, goalsScored desc , goalsConceeded asc ", LeagueTableEntity.class).list();
    }
}
