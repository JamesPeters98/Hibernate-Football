package frameworks;

import algorithms.FixtureGenerator;
import entities.FixturesEntity;
import entities.LeaguesEntity;
import entities.TeamsEntity;
import org.hibernate.Session;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class League implements Callable<League> {

    private LeaguesEntity leaguesEntity;
    private List<Team> teams;
    private Session session;
    private List<List<Fixture>> fixtures;

    private int season;

    public League(Session session, LeaguesEntity leaguesEntity, int seasonStart){
        this.leaguesEntity = leaguesEntity;
        this.session = session;
        this.season = seasonStart;
        teams = new ArrayList<>();
        fixtures = new ArrayList<>();
    }

    private void generateFixtures(){
        List<FixturesEntity> fixturesEntities = new ArrayList<>();

        fixtures = FixtureGenerator.getFixtures(teams,true, true);

        int gameweek = 1;
        for(List<Fixture> week : fixtures){
            for(Fixture fixture : week) {
                FixturesEntity fixturesEntity = new FixturesEntity();
                fixturesEntity.setGameweek(gameweek);
                fixturesEntity.setHometeamid(fixture.getHomeTeam().getTeam().getId());
                fixturesEntity.setAwayteamid(fixture.getAwayTeam().getTeam().getId());
                fixturesEntity.setLeagueid(leaguesEntity.getId());
                fixturesEntity.setSeasonId(season);

                fixturesEntities.add(fixturesEntity);
            }
            gameweek++;
        }

        session.beginTransaction();
        fixturesEntities.forEach(fixturesEntity -> session.saveOrUpdate(fixturesEntity));
        session.getTransaction().commit();
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
        System.out.println("Generated league: "+leaguesEntity.getLeaguename());
        return this;
    }
}
