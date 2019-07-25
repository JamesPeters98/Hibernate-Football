package com.jamesdpeters.optionmenu.options;

import com.jamesdpeters.entities.FixturesEntity;
import com.jamesdpeters.optionmenu.menus.Menu;
import com.jamesdpeters.utils.GameInfoStore;

import javax.swing.*;
import java.util.List;

public class TeamFixtureListOption extends Option {

    private static final int FIXTURE_AMOUNT = 8;
    private List<FixturesEntity> fixtures;

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
        int season = GameInfoStore.getGameInfo().getCurrentSeason();

        fixtures = session.createQuery("from FixturesEntity where (hometeamid = "+teamId+" or awayteamid = "+teamId+") and gameweek >= "+gameweek+" and gameweek < "+(gameweek+FIXTURE_AMOUNT)+" and seasonId="+season, FixturesEntity.class).list();
    }

    @Override
    protected void consoleInfo() {
        for(FixturesEntity fixturesEntity : fixtures){
            System.out.println(fixturesEntity.getHometeam().getName()+" vs "+fixturesEntity.getAwayteam().getName());
        }
    }

    @Override
    public JComponent getPanel() {
        return null;
    }
}
