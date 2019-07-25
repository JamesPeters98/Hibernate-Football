package com.jamesdpeters.optionmenu.options;

import com.jamesdpeters.optionmenu.menus.Menu;

import javax.swing.*;

public class ExitOption extends Option {
    public ExitOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "Exit";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Menu postRunMenu() {
        return null;
    }

    @Override
    protected void run() {
        System.exit(0);
    }

    @Override
    protected void consoleInfo() {

    }

    @Override
    public JComponent getPanel() {
        return null;
    }
}
