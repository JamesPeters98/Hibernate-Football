package optionmenu.frames;

import entities.LeagueTableEntity;
import helpers.LeagueTableHelper;
import optionmenu.options.Option;
import utils.GameInfoStore;
import utils.TableColumnAdjuster;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LeagueTablePanel extends Panel {

    private JPanel panel;
    private String[] columnNames;
    private Object[][] rowData;

    public LeagueTablePanel(Option option, JFrame frame){
        super(option,frame);

        //Create panel.
        panel = new JPanel(new FlowLayout());
    }

    @Override
    protected Object doInBackground() {
        List<LeagueTableEntity> leagueTable = LeagueTableHelper.getLeagueTable(
                GameInfoStore.getGameInfo().getCurrentSeason(),
                GameInfoStore.getGameInfo().getTeam().getLeagueid());

        columnNames = new String[]{
                "#",
                "Team",
                "Played",
                "Wins",
                "Draws",
                "Losses",
                "GS",
                "GC",
                "Points"
        };

        rowData = new Object[leagueTable.size()][columnNames.length];
        int pos = 0;
        for(LeagueTableEntity team : leagueTable){
            rowData[pos][0] = pos+1;
            rowData[pos][1] = team.getTeam().getName();
            rowData[pos][2] = team.getGamesPlayed();
            rowData[pos][3] = team.getWins();
            rowData[pos][4] = team.getDraws();
            rowData[pos][5] = team.getLosses();
            rowData[pos][6] = team.getGoalsScored();
            rowData[pos][7] = team.getGoalsConceeded();
            rowData[pos][8] = team.getPoints();

            pos++;
        }


        return null;
    }

    @Override
    protected void done() {
        super.done();

        JTable table = new JTable(rowData,columnNames);
        table.setFillsViewportHeight(true);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();

        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.NORTH);
        panel.add(getBackbutton(), BorderLayout.SOUTH);

//        frame.repaint();
//        frame.validate();
    }

    @Override
    public JComponent getPanel() {
        return panel;
    }
}
