package gui;

import frameworks.Season;
import optionmenu.menus.MainMenu;
import optionmenu.menus.Menu;

import javax.swing.*;

public class Gui {

    public Gui(Season season){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Football Simulator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Menu mainMenu = new MainMenu(frame,season);
        mainMenu.setupGuiElements();
        mainMenu.display();


    }
}
