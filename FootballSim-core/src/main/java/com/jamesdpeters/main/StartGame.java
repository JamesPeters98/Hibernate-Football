package com.jamesdpeters.main;

import com.jamesdpeters.entities.GameInfoEntity;
import com.jamesdpeters.entities.SeasonsEntity;
import com.jamesdpeters.frameworks.Season;
import com.jamesdpeters.gui.Gui;
import com.jamesdpeters.gui.ScannerInputField;
import com.jamesdpeters.gui.SetupGUI;
import com.jamesdpeters.utils.GameInfoStore;
import com.jamesdpeters.utils.InputUtil;
import com.jamesdpeters.utils.SessionStore;

public class StartGame {

    public static void main(String[] args) {
        try {
            //new ConsoleGui();
            new StartGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public StartGame() throws InterruptedException {
        SessionStore.setDB("GameSave");

        InputUtil.setInputField(new ScannerInputField());

        GameInfoStore.readGameInfo();
        GameInfoEntity gameInfo = GameInfoStore.getGameInfo();

        System.out.println("gameinfo - gamestarted: "+gameInfo.getGameStarted());


        if(!gameInfo.getGameStarted()) {
            //SetupGame.consoleSetup();

            SetupGUI setupGUI = new SetupGUI();

            System.out.println("Wait for UI to be setup!");
            //Wait for UI to be setup!
            synchronized (setupGUI.getSetupPanel()) {
                System.out.println("In synchronized! ");
                while (!setupGUI.getSetupPanel().isSETUP()) {
                    try{
                        System.out.println("Waiting!");
                        setupGUI.getSetupPanel().wait();
                    } catch (InterruptedException ignored){}
                }
            }

            System.out.println("No longer waiting! ");

            setupGUI.frame.setVisible(false);
            //setupGUI.frame.dispose();

            //Setup initial vars.
            SeasonsEntity seasonsEntity = new SeasonsEntity();
            seasonsEntity.setId(1);
            seasonsEntity.setGenerated(false);

            gameInfo = GameInfoStore.getGameInfo();
            gameInfo.setCurrentSeason(1);
            gameInfo.setCurrentSeasonEntity(seasonsEntity);
            gameInfo.setCurrentGameWeek(1);
        }

        System.out.println("GameInfo: week - "+GameInfoStore.getGameInfo().getCurrentGameWeek());

        Season season = new Season(SessionStore.getSession(), gameInfo.getLeagues());

        System.out.println(GameInfoStore.getGameInfo().toString());
        GameInfoStore.updateGameInfo();


        new Gui(season);
//        MainMenu menu = new MainMenu(season);
//        menu.consoleOpen();
    }


}
