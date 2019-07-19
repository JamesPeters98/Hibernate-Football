package frameworks;

import entities.FixturesEntity;
import entities.LeaguesEntity;
import entities.SeasonsEntity;
import helpers.LeagueTableHelper;
import helpers.SimulateMatchHelper;
import org.hibernate.Session;
import utils.GameInfoStore;
import utils.SessionStore;
import utils.Utils;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Season {

    private Session session;
    private SeasonsEntity seasonsEntity;
    private List<League> leagues = new ArrayList<>();
    private List<Integer> leagueIds;

    public static void main(String[] args) throws InterruptedException {
        SessionStore.setDB("TEST");
        new Season(SessionStore.getSession(),1,13,14,60,61);
    }

    public Season(Session session, Integer ...leagueIds) throws InterruptedException {
        this(session,Arrays.asList(leagueIds));
    }

    public Season(Session session, List<Integer> leagueIds) throws InterruptedException {
        this.session = session;
        this.leagueIds = leagueIds;
        this.seasonsEntity = getSeason();

        if(seasonsEntity.getGenerated()) System.out.println("Season already generated");
        else {
            System.out.println("Season wasn't generated");
            generateSeason();
        }
    }

    private SeasonsEntity getSeason(){
        try {
            return session.createQuery("from SeasonsEntity where id = " + GameInfoStore.getGameInfo().getCurrentSeason(), SeasonsEntity.class).getSingleResult();
        } catch (NoResultException e){
            System.out.println("No result for entity, generating new season");
            session.beginTransaction();
            SeasonsEntity seasonsEntity = new SeasonsEntity();
            seasonsEntity.setId(GameInfoStore.getGameInfo().getCurrentSeason());
            session.save(seasonsEntity);
            session.getTransaction().commit();
            GameInfoStore.updateGameInfo();
            return seasonsEntity;
        }
    }

    private void generateSeason() throws InterruptedException {
        System.out.println("Generating Season ");

        //Delete any fixtures for this season that may still be in the database.
        session.beginTransaction();
        session.createQuery("delete from FixturesEntity where seasonId = "+GameInfoStore.getGameInfo().getCurrentSeason()).executeUpdate();
        session.getTransaction().commit();

        //Load all leagues from DB.
        List<LeaguesEntity> leaguesEntities = getLeagues();

        //Multithreading league initialisations.
        ExecutorService executorService = Executors.newCachedThreadPool();

        leaguesEntities.forEach(leaguesEntity -> {
            League league = new League(SessionStore.getSession(),leaguesEntity,1);
            leagues.add(league);
        });

        executorService.invokeAll(leagues);
        executorService.shutdown();
        setGenerated();
    }

    private void setGenerated(){
        session.beginTransaction();
        seasonsEntity.setGenerated(true);
        session.saveOrUpdate(seasonsEntity);
        session.getTransaction().commit();
    }

    private List<LeaguesEntity> getLeagues(){
        if(leagueIds.size() == 0)
            return session.createQuery("from LeaguesEntity", LeaguesEntity.class).list();

        return session.createQuery("from LeaguesEntity where id IN("+ Utils.toCommaList(leagueIds) +")", LeaguesEntity.class).list();
    }

    public void simulateGameWeek(){
        long startTime = System.currentTimeMillis();
        int gameweek = GameInfoStore.getGameInfo().getCurrentGameWeek();

        //Multithreading league initialisations.
        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Callable<Void>> callables = new ArrayList<>();

        for(LeaguesEntity league : getLeagues()){
            //System.out.println("League Matches: "+league.getTotalMatches()+" gameweek: "+gameweek);
            if(gameweek <= league.getTotalMatches()) {

                Callable<Void> callable = () -> {
                    List<FixturesEntity> weekFixtures = SessionStore.getSession().createQuery("from FixturesEntity " +
                                    "where leagueid = " + league.getId() + " " +
                                    "and gameweek = " + gameweek + " " +
                                    "and seasonId = " + GameInfoStore.getGameInfo().getCurrentSeason(),
                            FixturesEntity.class).list();

                    SimulateMatchHelper.simulate(weekFixtures);
                    LeagueTableHelper.calculate(GameInfoStore.getGameInfo().getCurrentSeason(), league.getId(), gameweek);
                    return null;
                };

                callables.add(callable);

            } else {
                //Main Season fixtures have finished.
                System.out.println(league.getLeaguename()+" has finished! ");
            }
        }

        try {
            executorService.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GameInfoStore.getGameInfo().setCurrentGameWeek(gameweek+1);
        GameInfoStore.updateGameInfo();

        long endTime = System.currentTimeMillis();
        System.out.println("Calculated Gameweek in "+(endTime-startTime)+"ms");
    }
}
