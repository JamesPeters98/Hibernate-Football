package optionmenu.options;

import entities.FixtureResultEntity;
import entities.TeamsEntity;
import optionmenu.menus.Menu;
import utils.GameInfoStore;
import utils.Utils;

import java.util.List;

public class WeeklyFixtureResultsListOption extends Option {

    public WeeklyFixtureResultsListOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "Last Weeks Results";
    }

    @Override
    public String getDescription() {
        return "Show the league results for last week.";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {
        int leagueId = GameInfoStore.getGameInfo().getTeam().getLeagueid();
        int gameweek = GameInfoStore.getGameInfo().getCurrentGameWeek();

        List<FixtureResultEntity> fixtures = session.createQuery("from FixtureResultEntity  where fixture.leagueid = "+leagueId+" and fixture.gameweek = "+(gameweek-1), FixtureResultEntity.class).list();
        if(fixtures.size() == 0) System.out.println("No previous results to show.");
        for(FixtureResultEntity fixturesEntity : fixtures){
            TeamsEntity home = fixturesEntity.getFixture().getHometeam();
            TeamsEntity away = fixturesEntity.getFixture().getAwayteam();

            Utils.printPaddedMatchResult(home,away,fixturesEntity.getHomeGoals(),fixturesEntity.getAwayGoals(),30);
        }
    }
}
