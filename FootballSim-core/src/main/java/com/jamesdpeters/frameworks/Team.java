package com.jamesdpeters.frameworks;

import com.jamesdpeters.algorithms.BestTeamFormation;
import com.jamesdpeters.algorithms.BestTeamSheet;
import com.jamesdpeters.entities.PlayerRatingsEntity;
import com.jamesdpeters.entities.PlayersEntity;
import com.jamesdpeters.entities.PositionsEntity;
import com.jamesdpeters.entities.TeamsEntity;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Team {

    private TeamsEntity team;
    private List<PlayersEntity> players;
    private BestTeamFormation btf;
    private BestTeamSheet ts;
    protected Session session;

    private double attackRating;
    private double defenceRating;

    private double atkCoeff;
    private double defCoeff;

    private final static double AVG_RATING = 85;
    private final static double SKILL_GAP = 20;

    private double POTENTIAL_FACTOR = 0;

    public Team(Session session, TeamsEntity team){
        this(session);
        this.team = team;
    }

    public Team(Session session){
        this.session = session;
        session.clear();
    }

    protected List<PlayersEntity> getPlayers(){
        return session.createQuery("from PlayersEntity where team.id = "+team.getId(),PlayersEntity.class).list();
    }

    public String getTeamName(){
        return team.getName();
    }

    public boolean init() throws InterruptedException, ExecutionException, NoSuchFieldException, IllegalAccessException {
        players = getPlayers();
        if(players.size() < 11) return false;
        //double startTime = System.currentTimeMillis();
        calculateBestTeam();
        //System.out.println(team.getName()+" calculated best team in "+(System.currentTimeMillis()-startTime)+" ms");
        //startTime = System.currentTimeMillis();
        calculateCoeffs();
        //System.out.println(team.getName()+" calculated coeffs in "+(System.currentTimeMillis()-startTime)+" ms");
        //printInfo();

        return true;
    }

    public void printInfo(){
        System.out.println(
                "Team: "+getTeamName()+
                " Best Formation: "+btf.getBestTeamSheet().getFormation().getFormation()+
                " Rating: "+btf.getBestTeamSheet().getRating() +
                " Atk: "+ attackRating +
                " Def: "+ defenceRating);
    }

    public void printFormation(){
        System.out.println(getTeamName()+": Formation");
        System.out.println("---------------------");
        for(Map.Entry<PlayersEntity,PositionsEntity> entry : ts.getPlayerPositions().entrySet()){
            PlayersEntity player = entry.getKey();
            PositionsEntity pos = entry.getValue();
            PlayerRatingsEntity rating = player.getRating(pos.getId());
            System.out.println(player.getName()+" at "+pos.getPosition()+" rating: "+rating.getRating()+" atk: "+rating.getAttackrating()+" def: "+rating.getDefencerating());
        }
    }

    public void calculateBestTeam() throws InterruptedException, IllegalAccessException, NoSuchFieldException {
        //System.out.println("Player base: "+players.size());
        try{
            btf = new BestTeamFormation(session,players,POTENTIAL_FACTOR);
            ts = btf.getBestTeamSheet();
        } catch(ExecutionException e){
            System.err.println("Array out of bound for team: "+team.getName());
            e.printStackTrace();
        }
        //System.out.println("Best Formation: "+ts.getFormation().getFormation()+" - Rated: "+ts.getRating()+" - Weight: "+ts.getWeight());
    }

    public void calculateCoeffs(){
        double attackWeight = 0;
        double defenceWeight = 0;

        //PlayerRatingsUtil prUtil = new PlayerRatingsUtil();

        for(Map.Entry<PlayersEntity, PositionsEntity> entry : ts.getPlayerPositions().entrySet()){
            PlayersEntity player = entry.getKey();
            PositionsEntity pos = entry.getValue();
            PlayerRatingsEntity rating = player.getRating(pos.getId());

            double atk = rating.getAttackrating();
            double def = rating.getDefencerating();
            attackWeight += atk;
            defenceWeight += def;
            //System.out.println(player.getName()+" at "+pos.getPosition()+" rated: "+ Utils.getPosRating(player,pos)+" pos type: "+pos.getPositiontype().getPosition()+" attack rating: "+atk+" def weight: "+def);
        }

        attackRating = attackWeight/11;
        defenceRating = defenceWeight/11;

        atkCoeff = (attackRating-AVG_RATING)/SKILL_GAP;
        defCoeff = -(defenceRating-AVG_RATING)/SKILL_GAP ;
    }


    public double getAttackRating() {
        return attackRating;
    }

    public double getDefenceRating() {
        return defenceRating;
    }

    public double getAtkCoeff() {
        return atkCoeff;
    }

    public double getDefCoeff() {
        return defCoeff;
    }

    public double getTeamRating(){
        return btf.getBestTeamSheet().getRating();
    }

    public void setPotentialFactor(double potentialFactor){
        POTENTIAL_FACTOR = potentialFactor;
    }

    public TeamsEntity getTeam() {
        return team;
    }

    public BestTeamFormation getBestTeamFormation() {
        return btf;
    }

    public BestTeamSheet getBestTeamSheet() {
        return ts;
    }
}
