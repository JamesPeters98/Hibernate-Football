package optionmenu.frames;

import entities.FixtureResultEntity;
import entities.TeamsEntity;
import frameworks.Season;
import optionmenu.options.SimulateMatchOption;
import org.hibernate.Session;
import utils.GameInfoStore;
import utils.SessionStore;

import javax.persistence.NoResultException;
import javax.swing.*;
import java.awt.*;

public class PlayMatchPanel extends Panel {

    private int homeGoals;
    private int awayGoals;
    private TeamsEntity home;
    private TeamsEntity away;
    private JPanel panel;
    private Season season;

    public PlayMatchPanel(SimulateMatchOption option, JFrame frame, Season season){
        super(option,frame);
        this.season = season;

        //Create panel.
        panel = new JPanel(new GridLayout(9,9));
        panel.add(getStartButton(), BorderLayout.CENTER );

    }

    @Override
    protected Object doInBackground() {
        season.simulateGameWeek();
        Session session = SessionStore.getSession();
        int teamId = GameInfoStore.getGameInfo().getTeamId();
        int gameweek = GameInfoStore.getGameInfo().getCurrentGameWeek() - 1;
        int season = GameInfoStore.getGameInfo().getCurrentSeason();
        FixtureResultEntity resultEntity;
        try {
            resultEntity = session.createQuery("from FixtureResultEntity where fixture.seasonId = " + season + " and fixture.gameweek = " + gameweek + " and (fixture.hometeamid = " + teamId + " or fixture.awayteamid = " + teamId + ")", FixtureResultEntity.class).getSingleResult();

            homeGoals = resultEntity.getHomeGoals();
            awayGoals = resultEntity.getAwayGoals();
            home = resultEntity.getFixture().getHometeam();
            away =  resultEntity.getFixture().getAwayteam();

        } catch (NoResultException e){};
        return null;
    }

    private JButton getStartButton(){
        JButton button = new JButton("Start Game");
        button.addActionListener(e -> run());
        return button;
    }

    @Override
    protected void done() {
        super.done();
        panel.removeAll();
        panel.add(new JLabel(home.getName()+" "+homeGoals+" - "+awayGoals+" "+away.getName()), BorderLayout.CENTER);
        panel.add(getBackbutton(), BorderLayout.CENTER);
        frame.validate();
    }

    @Override
    public JComponent getPanel() {
        return panel;
    }
}
