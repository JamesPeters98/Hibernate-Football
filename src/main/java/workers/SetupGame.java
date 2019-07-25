package workers;

import entities.*;
import gui.InputField;
import gui.Output;
import org.hibernate.Session;
import utils.ASCII;
import utils.GameInfoStore;
import utils.InputUtil;
import utils.SessionStore;

import java.util.*;

public class SetupGame {

    public static void setup(int chosenTeam, List<Integer> leagues) {
        Session session = SessionStore.getSession();
        GameInfoEntity gameInfoEntity = session.createQuery("from GameInfoEntity", GameInfoEntity.class).getSingleResult();

        gameInfoEntity.setTeamId(chosenTeam);
        gameInfoEntity.setLeagues(leagues);
        gameInfoEntity.setGameStarted(true);

        GameInfoStore.updateGameInfo(gameInfoEntity);
    }

    public static void setup(Session session, int chosenTeam, int leagueId){
        //Add all leagues from chosen league country to Season.
        LeaguesEntity leaguesEntity = session.createQuery("from LeaguesEntity where id = "+leagueId, LeaguesEntity.class).getSingleResult();
        List<Integer> leagueIds = new ArrayList<>();
        for(LeaguesEntity leagueEnt : leaguesEntity.getCountry().getLeagues().values()){
            leagueIds.add(leagueEnt.getId());
        }

        setup(chosenTeam,leagueIds);
    }


    /**
     * Sets up a game from the console.
     */
    public static void consoleSetup() {
        Output.clear();
        System.out.println(ASCII.title);

        InputField inputField = InputUtil.getInputField();
        Session session = SessionStore.getSession();

        List<RegionsEntity> regions = session.createQuery("from RegionsEntity ", RegionsEntity.class).list();
        System.out.println("Chose what region you would like to play in: [Enter]");
        inputField.nextLine();

        for(RegionsEntity regionsEntity : regions){
            System.out.println(regionsEntity.getId()+": "+regionsEntity.getName());
        }

        System.out.println("Enter the id of the region you would like to chose: ");
        int regionId = inputField.nextInt();

        List<LeaguesEntity> leaguesEntities = session.createQuery("from LeaguesEntity where country.regionId = "+regionId, LeaguesEntity.class).list();

        System.out.println("Chose what league you would like to play in: [Enter]");
        inputField.nextLine();

        for(LeaguesEntity leaguesEntity : leaguesEntities){
            System.out.println(leaguesEntity.getId()+": "+leaguesEntity.getLeaguename());
        }

        System.out.println("Enter the id of the league you would like to chose: ");
        int league = inputField.nextInt();

        System.out.println("Chose what team you would like to play with: [Enter]");
        inputField.nextLine();

        List<TeamsEntity> teams = session.createQuery("from TeamsEntity where leagueid = "+league, TeamsEntity.class).list();

        for(TeamsEntity team : teams){
            System.out.println(team.getId()+": "+team.getName());
        }

        System.out.println("Enter the id of the team you would like to chose: ");
        int teamid = inputField.nextInt();

        //Add all leagues from chosen league country to Season.
        LeaguesEntity leaguesEntity = session.createQuery("from LeaguesEntity where id = "+league, LeaguesEntity.class).getSingleResult();
        List<Integer> leagueIds = new ArrayList<>();
        for(LeaguesEntity leagueEnt : leaguesEntity.getCountry().getLeagues().values()){
            leagueIds.add(leagueEnt.getId());
        }

        setup(teamid,leagueIds);

    }
}
