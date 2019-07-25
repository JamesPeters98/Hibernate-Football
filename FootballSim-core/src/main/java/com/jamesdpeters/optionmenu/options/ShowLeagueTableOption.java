package com.jamesdpeters.optionmenu.options;

import com.jamesdpeters.optionmenu.panels.LeagueTablePanel;
import com.jamesdpeters.optionmenu.menus.Menu;

import javax.swing.*;

public class ShowLeagueTableOption extends Option {

    LeagueTablePanel panel;

    public ShowLeagueTableOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "League Table";
    }

    @Override
    public String getDescription() {
        return "Show the current league table.";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {
        panel = new LeagueTablePanel(this,getParentMenu().getFrame());
        panel.run();
    }

    @Override
    protected void consoleInfo() {

    }

    @Override
    public JComponent getPanel() {
        return panel.getPanel();
    }
}
