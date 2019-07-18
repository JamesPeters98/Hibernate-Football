package optionmenu.menus;

import frameworks.Season;
import optionmenu.options.*;

import java.util.ArrayList;

public class FixtureMenu extends Menu {

    private ArrayList<Option> options;
    private Menu parentMenu;

    public FixtureMenu(Season season, Menu parentMenu) {
        super(season);
        this.parentMenu = parentMenu;
        options = new ArrayList<>();
        options.add(new FixtureListOption(this));
        options.add(new TeamFixtureListOption(this));
        options.add(new FixtureResultsListOption(this));
        options.add(new WeeklyFixtureResultsListOption(this));

    }

    @Override
    public ArrayList<Option> getOptions() {
        return options;
    }

    @Override
    public ArrayList<Menu> getSubMenus() {
        return null;
    }

    @Override
    public String getMenuName() {
        return "Fixture Lists & Results";
    }

    @Override
    public Menu getParentMenu() {
        return parentMenu;
    }
}
