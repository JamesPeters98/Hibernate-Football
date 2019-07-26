package com.jamesdpeters.optionmenu.panels;

import com.jamesdpeters.entities.FixtureResultEntity;
import com.jamesdpeters.entities.TeamsEntity;
import com.jamesdpeters.frameworks.Season;
import com.jamesdpeters.listeners.ProgressListener;
import com.jamesdpeters.optionmenu.options.SimulateMatchOption;
import com.jamesdpeters.utils.GameInfoStore;
import com.jamesdpeters.utils.SessionStore;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import javax.swing.*;
import java.awt.*;

public class PlayMatchPanel extends Panel {

    private int homeGoals;
    private int awayGoals;
    private TeamsEntity home;
    private TeamsEntity away;
    private JPanel panel;
    private JProgressBar bar;
    private Season season;
    private ProgressListener progressListener;

    public PlayMatchPanel(SimulateMatchOption option, JFrame frame, Season season){
        super(option,frame);
        this.season = season;

        //Create panel.
        panel = new JPanel(new FlowLayout());
        if(!isEndSeason()) panel.add(getStartButton(), BorderLayout.CENTER );

        bar = new JProgressBar();
        bar.setMaximum(100);
        panel.add(bar);

        //Check Simulation progress.
        progressListener = new ProgressListener() {
            @Override
            public void onProgressUpdate(int progress, int totalSize) {
                int percentage = 100*progress/totalSize;
                bar.setValue(percentage);
            }
        };

        season.addProgressListener(progressListener);

        if(isEndSeason()) execute();
    }

    private boolean isEndSeason(){
        return (GameInfoStore.getGameInfo().getCurrentGameWeek() > GameInfoStore.getGameInfo().getTeam().getLeague().getTotalMatches());
    }

    @Override
    protected Object doInBackground() {
        //Simulate the game week.
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
        session.close();
        return null;
    }

    private JButton getStartButton(){
        JButton button = new JButton("Start Game");
        button.setEnabled(true);
        button.addActionListener(e -> {
            button.setEnabled(false);
            execute();
            panel.add(new JLabel("Simulating!"));
            frame.validate();
        });
        return button;
    }

    @Override
    protected void done() {
        super.done();
        panel.removeAll();
        panel.add(getBackbutton(), BorderLayout.CENTER);
        panel.add(bar);
        if((home != null) && (away != null)) panel.add(new JLabel(home.getName()+" "+homeGoals+" - "+awayGoals+" "+away.getName()), BorderLayout.CENTER);
        frame.validate();
    }

    @Override
    public JComponent getPanel() {
        return panel;
    }
}
