package com.jamesdpeters.optionmenu.menus;

import com.jamesdpeters.frameworks.Season;
import com.jamesdpeters.optionmenu.options.Option;
import com.jamesdpeters.optionmenu.options.ShowPlayersOption;
import com.jamesdpeters.optionmenu.options.TeamSheetOption;

import javax.swing.*;
import java.util.ArrayList;

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
