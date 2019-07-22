package optionmenu.options;

import entities.FixtureResultEntity;
import entities.TeamsEntity;
import optionmenu.menus.Menu;
import utils.GameInfoStore;
import utils.Utils;

import javax.swing.*;
import java.util.List;

public class WeeklyFixtureResultsListOption extends Option {

    private List<FixtureResultEntity> fixtures;

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

        fixtures = session.createQuery("from FixtureResultEntity  where fixture.leagueid = "+leagueId+" and fixture.gameweek = "+(gameweek-1), FixtureResultEntity.class).list();

    }

    @Override
    protected void consoleInfo() {
        if(fixtures.size() == 0) System.out.println("No previous results to show.");
        for(FixtureResultEntity fixturesEntity : fixtures){
            TeamsEntity home = fixturesEntity.getFixture().getHometeam();
            TeamsEntity away = fixturesEntity.getFixture().getAwayteam();

            Utils.printPaddedMatchResult(home,away,fixturesEntity.getHomeGoals(),fixturesEntity.getAwayGoals(),30);
        }
    }

    @Override
    public JComponent getPanel() {
        return null;
    }
}
