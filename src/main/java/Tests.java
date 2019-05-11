import algorithms.BestTeamFormation;
import algorithms.BestTeamSheet;
import entities.PlayersEntity;
import org.hibernate.Session;
import utils.SessionStore;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Tests {

    static long startTime;
    static long endTime;

    public static void main(String[] args) throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException {

        startTime = System.currentTimeMillis();

        Session session = SessionStore.getSession();
        List<PlayersEntity> players = session.createQuery("from PlayersEntity where team.id = 5 or team.id = 11",PlayersEntity.class).list();

        System.out.println("Player base: "+players.size());

        BestTeamFormation btf = new BestTeamFormation(players,0.33);

        BestTeamSheet ts = btf.getBestTeamSheet();
        System.out.println("Best Formation: "+ts.getFormation().getFormation()+" - Rated: "+ts.getRating()+" - Weight: "+ts.getWeight());

        ts.getPlayerPositions().forEach((playersEntity, positionsEntity) -> {
            System.out.println(playersEntity.getName()+" at "+positionsEntity.getPosition());
        });

        endTime = System.currentTimeMillis();

        System.out.println("Time taken: "+(endTime-startTime)/1000+"s");

    }
}
