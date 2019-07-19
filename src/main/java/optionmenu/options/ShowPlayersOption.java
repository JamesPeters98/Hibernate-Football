package optionmenu.options;

import entities.PlayersEntity;
import optionmenu.menus.Menu;
import utils.GameInfoStore;

import java.util.List;

public class ShowPlayersOption extends Option {

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
        List<PlayersEntity> players = session.createQuery("from PlayersEntity where teamid = "+teamId+" order by positionId desc, overall desc",PlayersEntity.class).list();
        for(PlayersEntity player : players){
            System.out.println(player.getPosition().getPosition()+": "+player.getName()+" - "+Math.round(player.getRating(player.getPositionId()).getRating()));
        }
    }
}
