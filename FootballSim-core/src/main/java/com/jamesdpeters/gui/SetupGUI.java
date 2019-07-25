package com.jamesdpeters.gui;

import com.jamesdpeters.optionmenu.panels.SetupPanel;

import javax.swing.*;

public class SetupGUI {

    private SetupPanel setupPanel;
    public JFrame frame;

    public SetupGUI(){
        System.out.println("New Setup GUI");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Football Simulator Setup");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setupPanel = new SetupPanel();
        frame.add(setupPanel.getPanel());

        frame.setVisible(true);
        frame.pack();
        frame.setVisible( true );
        frame.setSize(500,500);
        frame.validate();
    }

    public SetupPanel getSetupPanel() {
        return setupPanel;
    }
}
