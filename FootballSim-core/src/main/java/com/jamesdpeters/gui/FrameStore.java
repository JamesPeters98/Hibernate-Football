package com.jamesdpeters.gui;

import javax.swing.*;

public class FrameStore {

    private static JFrame frame;

    final private static int INITIAL_HEIGHT = 500;
    final private static int INITIAL_WIDTH = 500;
    final private static String TITLE = "Football Sim";

    public static void setup(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(TITLE);
        frame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        frame.setLocationRelativeTo(null);  // *** this will center your app ***
    }

    public static void updateDimensions(){
        frame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
    }

    public static JFrame getFrame(){
        if(frame == null) setup();
        return frame;
    }

    public static void resetTitle(){
        checkIfSetup();
        frame.setTitle(TITLE);
    }

    public static void setSubTitle(String subTitle){
        checkIfSetup();
        frame.setTitle(TITLE+" - "+subTitle);
    }

    public static void clear(){
        checkIfSetup();
        frame.getContentPane().removeAll();
        frame.repaint();
    }

    private static void checkIfSetup(){
        if(frame == null) throw new NullPointerException("Frame not setup - call setup()");
    }
}
