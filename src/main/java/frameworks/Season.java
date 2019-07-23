package frameworks;

import entities.FixturesEntity;
import entities.LeaguesEntity;
import entities.SeasonsEntity;
import helpers.LeagueTableHelper;
import helpers.SimulateMatchHelper;
import listeners.ProgressListener;
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

    private List<ProgressListener> progressListeners;
    private List<Future<Void>> futures;
    private ExecutorService executorService;

    public Season(Session session, Integer ...leagueIds) throws InterruptedException {
        this(session,Arrays.asList(leagueIds));
    }

    public Season(Session session, List<Integer> leagueIds) throws InterruptedException {
        this.session = session;
        this.leagueIds = leagueIds;
        this.progressListeners = new ArrayList<>();
        init();
    }

    private void init() throws InterruptedException {
        System.out.println("Current Season: "+GameInfoStore.getGameInfo().getCurrentSeason());
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
        executorService = Executors.newCachedThreadPool();

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
        executorService = Executors.newCachedThreadPool();

        futures = new ArrayList<>();

        boolean simRest = false; //Whether to simulate the rest of the season for other leagues.

        for(LeaguesEntity league : getLeagues()){
            if(gameweek <= league.getTotalMatches()) {
                futures.add(executorService.submit(simulateWeek(league.getId(),gameweek)));
            } else if(league.getId() == GameInfoStore.getGameInfo().getTeam().getLeagueid()) {
                //Players Season has finished.
                simRest = true;
                System.out.println(league.getLeaguename()+" has finished! ");
            }
        }

        if(simRest) {
            finishSeasonsGames();
            System.out.println("Finished Seasons");
        }

        //Wait until all games are simulated.
        boolean complete = false;
        while(!complete) {
            int completed = 0;
            boolean tempComplete = true;
            for (Future<Void> future : futures) {
                if(future.isDone()) completed++;
                else if(tempComplete) tempComplete = false;
            }
            complete = tempComplete;
        }

        if(simRest){
            GameInfoStore.getGameInfo().nextSeason();
            GameInfoStore.updateGameInfo();
            System.out.println("Starting next season");
            try {
                init();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            GameInfoStore.getGameInfo().setCurrentGameWeek(gameweek+1);
            GameInfoStore.updateGameInfo();
        }


        long endTime = System.currentTimeMillis();
        System.out.println("Calculated Gameweek in "+(endTime-startTime)+"ms");
    }

    public void addProgressListener(ProgressListener progressListener){
        progressListeners.add(progressListener);
    }

    private void finishSeasonsGames(){
        boolean gamesRemain = true;
        int gameweek = GameInfoStore.getGameInfo().getCurrentGameWeek()+1;
        while(gamesRemain) {
            gamesRemain = false;
            for (LeaguesEntity league : getLeagues()) {
                if (gameweek <= league.getTotalMatches()) {
                    gamesRemain = true;
                    futures.add(executorService.submit(simulateWeek(league.getId(), gameweek)));
                } else  {
                    //League has finished.
                    //System.out.println(league.getLeaguename() + " has finished! ");
                }
            }
            gameweek++;
        }
    }

    private Callable<Void> simulateWeek(int leagueId, int gameweek){
        return () -> {
            List<FixturesEntity> weekFixtures = SessionStore.getSession().createQuery("from FixturesEntity " +
                            "where leagueid = " + leagueId + " " +
                            "and gameweek = " + gameweek + " " +
                            "and seasonId = " + GameInfoStore.getGameInfo().getCurrentSeason(),
                    FixturesEntity.class).list();

            for(ProgressListener progressListener : progressListeners) progressListener.addToTotal(weekFixtures.size());
            SimulateMatchHelper.simulate(weekFixtures,progressListeners);
            LeagueTableHelper.calculate(GameInfoStore.getGameInfo().getCurrentSeason(), leagueId, gameweek);
            return null;
        };
    }
}
