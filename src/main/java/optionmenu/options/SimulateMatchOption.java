package optionmenu.options;

import optionmenu.frames.PlayMatchPanel;
import optionmenu.menus.Menu;

import javax.swing.*;

public class SimulateMatchOption extends Option {

    PlayMatchPanel panel;

    public SimulateMatchOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "Play match";
    }

    @Override
    public String getDescription() {
        return "Play your match for this week.";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {
        panel = new PlayMatchPanel(this,getParentMenu().getFrame(),getParentMenu().getSeason());
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
