package optionmenu.options;

import main.StartGame;
import optionmenu.menus.Menu;
import utils.SessionStore;

import javax.swing.*;

public class ResetGameOption extends Option {
    public ResetGameOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "RESET";
    }

    @Override
    public String getDescription() {
        return "Warning, Resets Season!";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {

        SessionStore.resetDB();
        getParentMenu().getFrame().setVisible(false);
        getParentMenu().getFrame().dispose();

        //Start new game.
        new Thread(() -> {
            try {
                new StartGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @Override
    protected void consoleInfo() {

    }

    @Override
    public JComponent getPanel() {
        return null;
    }
}
