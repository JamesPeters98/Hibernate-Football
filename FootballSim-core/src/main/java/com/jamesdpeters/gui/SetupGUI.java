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

        frame = FrameStore.getFrame();

        setupPanel = new SetupPanel();
        frame.add(setupPanel.getPanel());

        //frame.pack();
        FrameStore.setSubTitle("Setup");
        frame.setVisible( true );
        frame.validate();
    }

    public SetupPanel getSetupPanel() {
        return setupPanel;
    }
}
