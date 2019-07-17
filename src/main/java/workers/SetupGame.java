package workers;

import Exceptions.NoDatabaseSelectedException;
import entities.GameInfoEntity;
import entities.LeaguesEntity;
import entities.TeamsEntity;
import frameworks.Team;
import org.hibernate.Session;
import utils.GameInfoStore;
import utils.SessionStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SetupGame {

    public static void setup(int chosenTeam, List<Integer> leagues) {
        Session session = SessionStore.getSession();
        GameInfoEntity gameInfoEntity = session.createQuery("from GameInfoEntity", GameInfoEntity.class).getSingleResult();

        gameInfoEntity.setTeamId(chosenTeam);
        gameInfoEntity.setLeagues(leagues);
        gameInfoEntity.setGameStarted(true);

        GameInfoStore.updateGameInfo(gameInfoEntity);
    }


    /**
     * Sets up a game from the console.
     */
    public static void consoleSetup() throws NoDatabaseSelectedException {
        Scanner scanner = new Scanner(System.in);
        Session session = SessionStore.getSession();

        List<LeaguesEntity> leaguesEntities = session.createQuery("from LeaguesEntity", LeaguesEntity.class).list();

        System.out.println("Chose what league you would like to play in: [Enter]");
        scanner.nextLine();

        for(LeaguesEntity leaguesEntity : leaguesEntities){
            System.out.println(leaguesEntity.getId()+": "+leaguesEntity.getLeaguename());
        }

        System.out.println("Enter the id of the league you would like to chose: ");
        int league = scanner.nextInt();

        System.out.println("Chose what team you would like to play with: [Enter]");
        scanner.nextLine();

        List<TeamsEntity> teams = session.createQuery("from TeamsEntity where leagueid = "+league, TeamsEntity.class).list();

        for(TeamsEntity team : teams){
            System.out.println(team.getId()+": "+team.getName());
        }

        System.out.println("Enter the id of the team you would like to chose: ");
        int teamid = scanner.nextInt();
        List<Integer> leagues = new ArrayList<>();
        leagues.add(league);

        setup(teamid,leagues);

    }
}
