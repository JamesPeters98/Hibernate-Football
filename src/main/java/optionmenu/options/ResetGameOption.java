package optionmenu.options;

import Exceptions.NoDatabaseSelectedException;
import main.StartGame;
import optionmenu.menus.Menu;

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

        session.beginTransaction();
        session.createQuery("delete from FixturesEntity").executeUpdate();
        session.createQuery("delete from FixtureResultEntity ").executeUpdate();
        session.createQuery("delete from GameInfoEntity").executeUpdate();
        session.createQuery("delete from LeagueTableEntity ").executeUpdate();
        session.createQuery("delete from SeasonsEntity").executeUpdate();
        session.getTransaction().commit();


        //Start new game.
        try {
            new StartGame();
        } catch (NoDatabaseSelectedException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
