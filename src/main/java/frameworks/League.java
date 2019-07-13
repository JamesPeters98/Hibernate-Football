package frameworks;

import algorithms.FixtureGenerator;
import entities.LeaguesEntity;
import entities.TeamsEntity;
import org.hibernate.Session;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class League implements Callable<League> {

    LeaguesEntity leaguesEntity;
    List<Team> teams;
    Session session;
    List<List<Fixture>> fixtures;

    public League(Session session, LeaguesEntity leaguesEntity){
        this.leaguesEntity = leaguesEntity;
        this.session = session;
        teams = new ArrayList<>();
        fixtures = new ArrayList<>();
    }

    public void generateFixtures(){
        fixtures = FixtureGenerator.getFixtures(teams,true);
    }

    private void setupTeams() throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException {
        for(TeamsEntity teamsEntity : leaguesEntity.getTeams()){
            Team team = new Team(session,teamsEntity);
            if(team.init()) teams.add(team);
        }
    }

    private void printLeagueInfo(){
        System.out.println(leaguesEntity.getLeaguename()+" Div: "+leaguesEntity.getDivision());
        for(Team team : teams){
            team.printInfo();
        }
    }

    public void printFixtures(){
        int gameweek = 1;
        for(List<Fixture> fixtureList : fixtures){
            System.out.println("Gameweek: "+gameweek);
            for(Fixture fixture : fixtureList){
                System.out.println(fixture.getHomeTeam().getTeamName()+" vs "+fixture.getAwayTeam().getTeamName());
            }
            gameweek++;
        }
    }


    public LeaguesEntity getLeaguesEntity() {
        return leaguesEntity;
    }

    public List<Team> getTeams() {
        return teams;
    }


    
    @Override
    public League call() throws InterruptedException, ExecutionException, NoSuchFieldException, IllegalAccessException {
        setupTeams();
        generateFixtures();
        return this;
    }
}
