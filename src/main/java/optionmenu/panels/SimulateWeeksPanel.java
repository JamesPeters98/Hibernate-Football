package optionmenu.panels;

import entities.FixtureResultEntity;
import entities.TeamsEntity;
import frameworks.Season;
import listeners.ProgressListener;
import optionmenu.options.Option;
import optionmenu.options.SimulateMatchOption;
import org.hibernate.Session;
import utils.GameInfoStore;
import utils.SessionStore;

import javax.persistence.NoResultException;
import javax.swing.*;
import java.awt.*;

public class SimulateWeeksPanel extends Panel {

    private int homeGoals;
    private int awayGoals;
    private TeamsEntity home;
    private TeamsEntity away;
    private JPanel panel;
    private JProgressBar bar;
    private JSlider slider;
    private Season season;
    private ProgressListener progressListener;

    private int weeks = 0;

    public SimulateWeeksPanel(Option option, JFrame frame, Season season){
        super(option,frame);
        this.season = season;

        //Create panel.
        panel = new JPanel(new FlowLayout());
        panel.add(getStartButton(), BorderLayout.CENTER );

        int weeksLeft = GameInfoStore.getGameInfo().getTeam().getLeague().getTotalMatches()-GameInfoStore.getGameInfo().getCurrentGameWeek()+1;
        slider = new JSlider(JSlider.HORIZONTAL,1,weeksLeft,1);

        panel.add(slider);

//        bar = new JProgressBar();
//        bar.setMaximum(100);
//        panel.add(bar);

        //Check Simulation progress.
        progressListener = new ProgressListener() {
            @Override
            public void onProgressUpdate(int progress, int totalSize) {
                int percentage = 100*progress/totalSize;
                bar.setValue(percentage);
            }
        };

        season.addProgressListener(progressListener);

    }

    @Override
    protected Object doInBackground() {
        //Simulate the game week.
        season.simulateGameWeek(weeks);

//        Session session = SessionStore.getSession();
//        int teamId = GameInfoStore.getGameInfo().getTeamId();
//        int gameweek = GameInfoStore.getGameInfo().getCurrentGameWeek() - 1;
//        int season = GameInfoStore.getGameInfo().getCurrentSeason();
//        FixtureResultEntity resultEntity;
//        try {
//            resultEntity = session.createQuery("from FixtureResultEntity where fixture.seasonId = " + season + " and fixture.gameweek = " + gameweek + " and (fixture.hometeamid = " + teamId + " or fixture.awayteamid = " + teamId + ")", FixtureResultEntity.class).getSingleResult();
//
//            homeGoals = resultEntity.getHomeGoals();
//            awayGoals = resultEntity.getAwayGoals();
//            home = resultEntity.getFixture().getHometeam();
//            away =  resultEntity.getFixture().getAwayteam();
//
//        } catch (NoResultException e){};
        return null;
    }

    private JButton getStartButton(){
        JButton button = new JButton("Start Game");
        button.setEnabled(true);
        button.addActionListener(e -> {
            button.setEnabled(false);

            weeks = slider.getValue();
            System.err.println("Slider value: "+weeks);

            panel.remove(slider);
            bar = new JProgressBar();
            bar.setMaximum(100);
            panel.add(bar);

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
