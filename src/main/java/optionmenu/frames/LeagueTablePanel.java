package optionmenu.frames;

import entities.LeagueTableEntity;
import helpers.LeagueTableHelper;
import optionmenu.options.Option;
import utils.GameInfoStore;

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
        panel = new JPanel();
    }

    @Override
    protected Object doInBackground() {
        List<LeagueTableEntity> leagueTable = LeagueTableHelper.getLeagueTable(
                GameInfoStore.getGameInfo().getCurrentSeason(),
                GameInfoStore.getGameInfo().getTeam().getLeagueid());

        rowData = new Object[leagueTable.size()][6];
        int pos = 0;
        for(LeagueTableEntity team : leagueTable){
            rowData[pos][0] = pos+1;
            rowData[pos][1] = team.getTeam().getName();
            rowData[pos][2] = team.getPoints();
            rowData[pos][3] = team.getWins();
            rowData[pos][4] = team.getDraws();
            rowData[pos][5] = team.getLosses();
            pos++;
        }


        columnNames = new String[]{
                "#",
                "Team",
                "Points",
                "Wins",
                "Draws",
                "Losses"
        };

        return null;
    }

    @Override
    protected void done() {
        super.done();

        JTable table = new JTable(rowData,columnNames);
        table.setFillsViewportHeight(true);

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
