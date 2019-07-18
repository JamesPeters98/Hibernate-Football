package optionmenu.options;

import entities.PlayerRatingsEntity;
import entities.PlayersEntity;
import entities.PositionsEntity;
import frameworks.Team;
import optionmenu.menus.Menu;
import utils.GameInfoStore;
import utils.SessionStore;
import utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class TeamSheetOption extends Option {
    public TeamSheetOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "Team Sheet";
    }

    @Override
    public String getDescription() {
        return "View your teams players";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }


    @Override
    protected void run() {
        Team team = new Team(SessionStore.getSession(),GameInfoStore.getGameInfo().getTeam());
        try {
            team.init();
            HashMap<PlayersEntity, PositionsEntity> players = team.getBestTeamSheet().getPlayerPositions();
            for(Map.Entry<PlayersEntity,PositionsEntity> entry : players.entrySet()){
                PlayersEntity player = entry.getKey();
                PositionsEntity pos = entry.getValue();
                PlayerRatingsEntity rating = player.getRating(pos.getId());
                System.out.println(pos.getPosition()+": "+player.getName()+" rating: "+rating.getRating());
            }
        } catch (InterruptedException | ExecutionException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
