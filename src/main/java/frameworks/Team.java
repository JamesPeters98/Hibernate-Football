package frameworks;

import algorithms.BestTeamFormation;
import algorithms.BestTeamSheet;
import entities.PlayerRatingsEntity;
import entities.PlayersEntity;
import entities.PositionsEntity;
import entities.TeamsEntity;
import org.hibernate.Session;
import utils.Utils;

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

    public void init() throws InterruptedException, ExecutionException, NoSuchFieldException, IllegalAccessException {
        players = getPlayers();
        //double startTime = System.currentTimeMillis();
        calculateBestTeam();
        //Utils.logger.debug(team.getName()+" calculated best team in "+(System.currentTimeMillis()-startTime)+" ms");
        //startTime = System.currentTimeMillis();
        calculateCoeffs();
        //Utils.logger.debug(team.getName()+" calculated coeffs in "+(System.currentTimeMillis()-startTime)+" ms");
        //printInfo();
    }

    public void printInfo(){
        Utils.logger.debug(
                "Team: "+getTeamName()+
                " Best Formation: "+btf.getBestTeamSheet().getFormation().getFormation()+
                " Atk: "+ attackRating +
                " Def: "+ defenceRating);
    }

    public void printFormation(){
        Utils.logger.info(getTeamName()+": Formation");
        Utils.logger.info("---------------------");
        for(Map.Entry<PlayersEntity,PositionsEntity> entry : ts.getPlayerPositions().entrySet()){
            PlayersEntity player = entry.getKey();
            PositionsEntity pos = entry.getValue();
            Utils.logger.info(player.getName()+" at "+pos.getPosition());
        }
    }

    public void calculateBestTeam() throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException {
        //Utils.logger.info("Player base: "+players.size());
        btf = new BestTeamFormation(session,players,0);
        ts = btf.getBestTeamSheet();
        //Utils.logger.info("Best Formation: "+ts.getFormation().getFormation()+" - Rated: "+ts.getRating()+" - Weight: "+ts.getWeight());
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
            //Utils.logger.info(player.getName()+" at "+pos.getPosition()+" rated: "+ Utils.getPosRating(player,pos)+" pos type: "+pos.getPositiontype().getPosition()+" attack rating: "+atk+" def weight: "+def);
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
}
