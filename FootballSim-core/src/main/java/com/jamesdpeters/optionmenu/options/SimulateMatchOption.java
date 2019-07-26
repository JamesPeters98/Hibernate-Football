package com.jamesdpeters.optionmenu.options;

import com.jamesdpeters.optionmenu.menus.Menu;
import com.jamesdpeters.optionmenu.panels.PlayMatchPanel;
import com.jamesdpeters.utils.GameInfoStore;

import javax.swing.*;

public class SimulateMatchOption extends Option {

    PlayMatchPanel panel;

    public SimulateMatchOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        if(GameInfoStore.getGameInfo().getCurrentGameWeek() > GameInfoStore.getGameInfo().getTeam().getLeague().getTotalMatches()) return "End Season";
        return "Play match";
    }

    @Override
    public String getDescription() {
        return "Play your match for this week.";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {
        panel = new PlayMatchPanel(this,getParentMenu().getFrame(),getParentMenu().getSeason());
    }

    @Override
    protected void consoleInfo() {
        //System.out.println(home.getName()+" "+homeGoals+" - "+awayGoals+" "+away.getName());
    }

    @Override
    public JComponent getPanel() {
        return panel.getPanel();
    }
}
