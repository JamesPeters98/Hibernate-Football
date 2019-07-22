package optionmenu.options;

import entities.PlayersEntity;
import optionmenu.menus.Menu;
import utils.GameInfoStore;

import javax.swing.*;
import java.util.List;

public class ShowPlayersOption extends Option {

    private List<PlayersEntity> players;

    public ShowPlayersOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "All Players";
    }

    @Override
    public String getDescription() {
        return "Show all your players";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {
        int teamId = GameInfoStore.getGameInfo().getTeamId();
        players = session.createQuery("from PlayersEntity where teamid = "+teamId+" order by positionId desc, overall desc",PlayersEntity.class).list();
    }

    @Override
    protected void consoleInfo() {
        for(PlayersEntity player : players){
            System.out.println(player.getPosition().getPosition()+": "+player.getName()+" - "+Math.round(player.getRating(player.getPositionId()).getRating()));
        }
    }

    @Override
    public JComponent getPanel() {
        return null;
    }
}
