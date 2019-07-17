package optionmenu.options;

import entities.FixtureResultEntity;
import entities.TeamsEntity;
import optionmenu.menus.Menu;
import utils.GameInfoStore;

import javax.persistence.NoResultException;

public class SimulateMatchOption extends Option {

    public SimulateMatchOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "Play match";
    }

    @Override
    public String getDescription() {
        return "Play your match for this week.";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {
        System.out.println("Simulating Games...");
        getParentMenu().getSeason().simulateGameWeek();

        int teamId = GameInfoStore.getGameInfo().getTeamId();
        int gameweek = GameInfoStore.getGameInfo().getCurrentGameWeek() - 1;
        int season = GameInfoStore.getGameInfo().getCurrentSeason();

        FixtureResultEntity resultEntity;

        try {
            resultEntity = session.createQuery("from FixtureResultEntity where fixture.seasonId = " + season + " and fixture.gameweek = " + gameweek + " and (fixture.hometeamid = " + teamId + " or fixture.awayteamid = " + teamId + ")", FixtureResultEntity.class).getSingleResult();

            int homeGoals = resultEntity.getHomeGoals();
            int awayGoals = resultEntity.getAwayGoals();
            TeamsEntity home = resultEntity.getFixture().getHometeam();
            TeamsEntity away =  resultEntity.getFixture().getAwayteam();

            System.out.println(home.getName()+" "+homeGoals+" - "+awayGoals+" "+away.getName());
        } catch (NoResultException e){

        }


    }
}
