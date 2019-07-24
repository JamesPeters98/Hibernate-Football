package optionmenu.options;

import entities.FixtureResultEntity;
import entities.TeamsEntity;
import optionmenu.menus.Menu;
import utils.GameInfoStore;
import utils.Utils;

import javax.swing.*;
import java.util.List;

public class FixtureResultsListOption extends Option {

    private static final int FIXTURE_AMOUNT = 8;
    private List<FixtureResultEntity> fixtures;

    public FixtureResultsListOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "Previous Results";
    }

    @Override
    public String getDescription() {
        return "Show your teams previous results.";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {
        int teamId = GameInfoStore.getGameInfo().getTeam().getId();
        int gameweek = GameInfoStore.getGameInfo().getCurrentGameWeek();

        fixtures = session.createQuery("from FixtureResultEntity  where (fixture.hometeamid = "+teamId+" or fixture.awayteamid = "+teamId+") order by id desc, fixture.gameweek desc", FixtureResultEntity.class).list();
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
