package com.jamesdpeters.optionmenu.options;

import com.jamesdpeters.optionmenu.menus.Menu;
import com.jamesdpeters.optionmenu.panels.SimulateWeeksPanel;

import javax.swing.*;

public class SimulateWeeksOption extends Option {

    SimulateWeeksPanel panel;

    public SimulateWeeksOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "Simulate season";
    }

    @Override
    public String getDescription() {
        return "Simulate a set amount of weeks.";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {
        panel = new SimulateWeeksPanel(this, getParentMenu().getFrame(),getParentMenu().getSeason());
        //panel = new PlayMatchPanel(this,getParentMenu().getFrame(),getParentMenu().getSeason());
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
