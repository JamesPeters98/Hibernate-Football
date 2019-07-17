package optionmenu.options;

import entities.FixturesEntity;
import entities.LeaguesEntity;
import optionmenu.menus.MainMenu;
import optionmenu.menus.Menu;
import org.hibernate.Session;
import utils.GameInfoStore;
import utils.SessionStore;

import java.util.List;

public class TeamFixtureListOption extends Option {

    private static final int FIXTURE_AMOUNT = 8;

    public TeamFixtureListOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "Team Fixtures";
    }

    @Override
    public String getDescription() {
        return "Show your teams next fixtures";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {
        int teamId = GameInfoStore.getGameInfo().getTeam().getId();
        int gameweek = GameInfoStore.getGameInfo().getCurrentGameWeek();

        List<FixturesEntity> fixtures = session.createQuery("from FixturesEntity where (hometeamid = "+teamId+" or awayteamid = "+teamId+") and gameweek >= "+gameweek+" and gameweek < "+(gameweek+FIXTURE_AMOUNT), FixturesEntity.class).list();
        for(FixturesEntity fixturesEntity : fixtures){
            System.out.println(fixturesEntity.getHometeam().getName()+" vs "+fixturesEntity.getAwayteam().getName());
        }
    }
}
