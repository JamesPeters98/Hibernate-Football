package optionmenu.menus;

import frameworks.Season;
import optionmenu.options.*;

import java.util.ArrayList;

public class MainMenu extends Menu {

    private ArrayList<Option> options;
    private ArrayList<Menu> menus;

    public MainMenu(Season season){
        super(season);
        options = new ArrayList<>();
        options.add(new SimulateMatchOption(this));
        options.add(new ShowLeagueTableOption(this));
        options.add(new ResetGameOption(this));
        options.add(new ExitOption(this));

        menus = new ArrayList<>();
        menus.add(new FixtureMenu(season,this));
        menus.add(new TeamInfoMenu(season, this));
    }

    @Override
    public ArrayList<Option> getOptions() {
        return options;
    }

    @Override
    public ArrayList<Menu> getSubMenus() {
        return menus;
    }

    @Override
    public String getMenuName() {
        return "Main Menu";
    }

    @Override
    public Menu getParentMenu() {
        return null;
    }

}
