package main;

import Exceptions.NoDatabaseSelectedException;
import entities.GameInfoEntity;
import frameworks.Season;
import gui.Gui;
import gui.InputField;
import gui.Output;
import optionmenu.menus.MainMenu;
import utils.ASCII;
import utils.GameInfoStore;
import utils.InputUtil;
import utils.SessionStore;
import gui.TextAreaOutputStream;
import workers.SetupGame;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

public class StartGame {

    public static void main(String[] args) {
        try {
            new Gui();
            new StartGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public StartGame() throws InterruptedException {
        SessionStore.setDB("GameSave");

        if(!GameInfoStore.readGameInfo()){

//            //TODO Default DB will include all player info, so will eliminate need to download data.
//            //Download data
//            try {
//                DownloadData.scrape();
//            } catch (ExecutionException ex) {
//                ex.printStackTrace();
//            }
        }
        GameInfoEntity gameInfo = GameInfoStore.getGameInfo();

        System.out.println("gameinfo - gamestarted: "+gameInfo.getGameStarted());


        if(!gameInfo.getGameStarted()) {
            SetupGame.consoleSetup();

            gameInfo = GameInfoStore.getGameInfo();
            gameInfo.setCurrentSeason(1);
            gameInfo.setCurrentGameWeek(1);
        }

        System.out.println("GameInfo: week - "+GameInfoStore.getGameInfo().getCurrentGameWeek());

        Season season = new Season(SessionStore.getSession(), gameInfo.getLeagues());

        System.out.println(GameInfoStore.getGameInfo().toString());
        GameInfoStore.updateGameInfo();

        MainMenu menu = new MainMenu(season);
        menu.open();
    }


}
