package optionmenu.options;

import entities.FixturesEntity;
import entities.LeaguesEntity;
import optionmenu.menus.Menu;
import utils.GameInfoStore;

import java.util.List;

public class FixtureListOption extends Option {

    public FixtureListOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "Weekly Fixture List";
    }

    @Override
    public String getDescription() {
        return "Show the fixtures for this week";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {
        LeaguesEntity league = GameInfoStore.getGameInfo().getTeam().getLeague();

        List<FixturesEntity> fixtures = session.createQuery("from FixturesEntity where leagueid = "+league.getId()+" and gameweek = "+GameInfoStore.getGameInfo().getCurrentGameWeek(), FixturesEntity.class).list();
        for(FixturesEntity fixturesEntity : fixtures){
            System.out.println(fixturesEntity.getHometeam().getName()+" vs "+fixturesEntity.getAwayteam().getName());
        }
    }
}
