package optionmenu.menus;

import frameworks.Season;
import optionmenu.options.*;

import java.util.ArrayList;

public class MainMenu extends Menu {

    private ArrayList<Option> options;

    public MainMenu(Season season){
        super(season);
        options = new ArrayList<>();
        options.add(new FixtureListOption(this));
        options.add(new TeamFixtureListOption(this));
        options.add(new FixtureResultsListOption(this));
        options.add(new SimulateMatchOption(this));
        options.add(new WeeklyFixtureResultsListOption(this));
        options.add(new ShowLeagueTableOption(this));
    }

    @Override
    public ArrayList<Option> getOptions() {
        return options;
    }

    @Override
    public String getMenuName() {
        return "Main Menu";
    }

}
