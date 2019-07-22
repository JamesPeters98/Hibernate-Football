package optionmenu.menus;

import frameworks.Season;
import optionmenu.options.Option;
import optionmenu.options.ShowPlayersOption;
import optionmenu.options.TeamSheetOption;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TeamInfoMenu extends Menu {

    private ArrayList<Option> optionList;

    public TeamInfoMenu(JFrame frame, Season season, Menu parentMenu) {
        super(frame, season, parentMenu);
        optionList = new ArrayList<>();
        optionList.add(new TeamSheetOption(this));
        optionList.add(new ShowPlayersOption(this));
    }

    @Override
    public ArrayList<Option> getOptions() {
        return optionList;
    }

    @Override
    public ArrayList<Menu> getSubMenus() {
        return null;
    }

    @Override
    public String getMenuName() {
        return "Team Info";
    }


}
