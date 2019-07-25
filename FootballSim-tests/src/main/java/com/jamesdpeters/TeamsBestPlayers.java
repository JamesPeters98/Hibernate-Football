package com.jamesdpeters;

import com.jamesdpeters.exceptions.NoDatabaseSelectedException;
import com.jamesdpeters.entities.NationalityEntity;
import com.jamesdpeters.entities.PlayersEntity;
import com.jamesdpeters.frameworks.NationalTeam;
import com.jamesdpeters.frameworks.Team;
import org.hibernate.Session;
import com.jamesdpeters.utils.SessionStore;
import com.jamesdpeters.utils.Utils;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TeamsBestPlayers {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException, NoDatabaseSelectedException {
        SessionStore.setDB("TEST");
        Session session = SessionStore.getSession();
        //TeamsEntity teamsEntity = session.createQuery("from TeamsEntity where id = 9",TeamsEntity.class).getSingleResult();

        NationalityEntity nationalityEntity = session.createQuery("from NationalityEntity where id = 18",NationalityEntity.class).getSingleResult();
        Team team = new NationalTeam(session,nationalityEntity);

        //Team team = new Team(session,teamsEntity);
        team.setPotentialFactor(1);
        team.init();
        team.printInfo();
        team.printFormation();
        Utils.logger.info("----------------------------");
        List<PlayersEntity> players = session.createQuery("from PlayersEntity where nationalityId = 18 order by overall desc", PlayersEntity.class).list();
        for (PlayersEntity player : players) {
            Utils.logger.info(player.getName()+" at "+player.getPosition().getPosition()+" rating: "+player.getRating(player.getPositionId()));
        }
    }
}
