package com.jamesdpeters.frameworks;

import com.jamesdpeters.utils.Utils;

public class VersusGame {

    private Team home;
    private Team away;

    public VersusGame(Team home, Team away){
        this.home = home;
        this.away = away;
//        Utils.logger.debug(home.getTeamName()+" atk: "+home.getAtkCoeff()+" def: "+home.getDefCoeff());
//        Utils.logger.debug(home.getTeamName()+" atk: "+home.getAttackRating()+" def: "+home.getDefenceRating());
//        Utils.logger.debug(away.getTeamName()+" atk: "+away.getAtkCoeff()+" def: "+away.getDefCoeff());
//        Utils.logger.debug(away.getTeamName()+" atk: "+away.getAttackRating()+" def: "+away.getDefenceRating());
    }

    private double getHomeGoals(){
        return Math.exp(home.getAtkCoeff()+away.getDefCoeff()+0.2);
    }

    private double getAwayGoals(){
        return Math.exp(away.getAtkCoeff()+home.getDefCoeff());
    }

    public int generateHomeGoals(){
        return Utils.poisson(getHomeGoals());
    }

    public int generateAwayGoals(){
        return Utils.poisson(getAwayGoals());
    }

    public void printInfo(){
        Utils.logger.debug(home.getTeamName()+" goals: "+getHomeGoals());
        Utils.logger.debug(away.getTeamName()+" goals: "+getAwayGoals());
    }

    public Team getHome() {
        return home;
    }

    public Team getAway() {
        return away;
    }
}
