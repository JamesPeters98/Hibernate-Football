package optionmenu.options;

import entities.LeagueTableEntity;
import helpers.LeagueTableHelper;
import optionmenu.menus.Menu;
import utils.GameInfoStore;

import java.util.List;

public class ShowLeagueTableOption extends Option {

    public ShowLeagueTableOption(Menu menu) {
        super(menu);
    }

    @Override
    public String getTitle() {
        return "League Table";
    }

    @Override
    public String getDescription() {
        return "Show the current league table.";
    }

    @Override
    public Menu postRunMenu() {
        return getParentMenu();
    }

    @Override
    protected void run() {
        List<LeagueTableEntity> leagueTable = LeagueTableHelper
                .getLeagueTable(
                        GameInfoStore
                                .getGameInfo()
                                .getCurrentSeason(),
                        GameInfoStore
                                .getGameInfo()
                                .getTeam()
                                .getLeagueid());

        if(leagueTable.size() == 0) System.out.println("Season hasn't begun! ");

        for(int i = 0; i < leagueTable.size(); i++){
            LeagueTableEntity team = leagueTable.get(i);
            System.out.println((i+1)+". "+team.getTeam().getName()+" - P:"+team.getPoints()+" W:"+team.getWins()+" D:"+team.getDraws()+" L:"+team.getLosses()+" GF:"+team.getGoalsScored()+" GC:"+team.getGoalsConceeded());
        }
    }
}
