package optionmenu.options;

import entities.PlayerRatingsEntity;
import entities.PlayersEntity;
import entities.PositionsEntity;
import frameworks.Team;
import optionmenu.menus.Menu;
import utils.GameInfoStore;
import utils.SessionStore;

import java.util.HashMap;
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
        return "View your current starting 11.";
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
                System.out.println(pos.getPosition()+": "+player.getName()+" rating: "+Math.round(rating.getRating()));
            }

            System.out.println();
            System.out.println(
                    "Best Formation: "+team.getBestTeamSheet().getFormation().getFormation()+
                    " Rating: "+team.getBestTeamSheet().getRating() +
                    " Atk: "+ team.getAttackRating() +
                    " Def: "+ team.getDefenceRating());

        } catch (InterruptedException | ExecutionException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
