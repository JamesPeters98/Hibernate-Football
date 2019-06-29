import Exceptions.NoDatabaseSelectedException;
import entities.TeamsEntity;
import frameworks.Team;
import org.hibernate.Session;
import utils.SessionStore;

import java.util.concurrent.ExecutionException;

public class TeamsBestPlayers {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException, NoDatabaseSelectedException {
        SessionStore.setDB("TEST");
        Session session = SessionStore.getSession();
        TeamsEntity teamsEntity = session.createQuery("from TeamsEntity where id = 9",TeamsEntity.class).getSingleResult();
        Team team = new Team(session,teamsEntity);
        team.init();
        team.printInfo();
        team.printFormation();
    }
}
