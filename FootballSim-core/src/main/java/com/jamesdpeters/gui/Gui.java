package com.jamesdpeters.gui;

import com.jamesdpeters.frameworks.Season;
import com.jamesdpeters.optionmenu.menus.MainMenu;
import com.jamesdpeters.optionmenu.menus.Menu;

import javax.swing.*;

public class Gui {

    public Gui(Season season){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        FrameStore.clear();
        FrameStore.resetTitle();
        JFrame frame = FrameStore.getFrame();

        Menu mainMenu = new MainMenu(frame,season);
        mainMenu.setupGuiElements();
        mainMenu.display();
    }
}
