package tests;

import Exceptions.NoDatabaseSelectedException;
import entities.PlayersEntity;
import entities.TeamsEntity;
import frameworks.Team;
import org.hibernate.Session;
import utils.SessionStore;
import utils.Utils;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TeamsBestPlayers {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException, NoDatabaseSelectedException {
        SessionStore.setDB("TEST");
        Session session = SessionStore.getSession();
        TeamsEntity teamsEntity = session.createQuery("from TeamsEntity where id = 9",TeamsEntity.class).getSingleResult();
        Team team = new Team(session,teamsEntity);
        team.setPotentialFactor(0.5);
        team.init();
        team.printInfo();
        team.printFormation();
        Utils.logger.info("----------------------------");
        List<PlayersEntity> players = session.createQuery("from PlayersEntity where teamid = 9 order by overall desc", PlayersEntity.class).list();
        for (PlayersEntity player : players) {
            Utils.logger.info(player.getName()+" at "+player.getPosition().getPosition()+" rating: "+player.getRating(player.getPositionId()));
        }
    }
}
