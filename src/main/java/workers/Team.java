package workers;

import algorithms.BestTeamFormation;
import algorithms.BestTeamSheet;
import entities.PlayersEntity;
import entities.PositionsEntity;
import entities.TeamsEntity;
import org.hibernate.Session;
import utils.PlayerRatingsUtil;
import utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Team {

    private TeamsEntity team;
    private List<PlayersEntity> players;
    private BestTeamFormation btf;
    private BestTeamSheet ts;
    private Session session;

    private double attackCoeff;
    private double defenceCoeff;

    public Team(Session session, TeamsEntity team){
        players = session.createQuery("from PlayersEntity where team.id = "+team.getId(),PlayersEntity.class).list();
        this.team = team;
        this.session = session;
        session.clear();
    }

    public void init() throws InterruptedException, ExecutionException, NoSuchFieldException, IllegalAccessException {
        //double startTime = System.currentTimeMillis();
        calculateBestTeam();
        //Utils.logger.debug(team.getName()+" calculated best team in "+(System.currentTimeMillis()-startTime)+" ms");
        //startTime = System.currentTimeMillis();
        calculateCoeffs();
        //Utils.logger.debug(team.getName()+" calculated coeffs in "+(System.currentTimeMillis()-startTime)+" ms");
    }

    public void printInfo(){
        Utils.logger.debug(
                "Team: "+team.getName()+
                " Best Formation: "+btf.getBestTeamSheet().getFormation().getFormation()+
                " Atk: "+attackCoeff+
                " Def: "+defenceCoeff);
    }

    public void printFormation(){
        Utils.logger.info(team.getName()+": Formation");
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

    public void calculateCoeffs() throws NoSuchFieldException, IllegalAccessException {
        double attackWeight = 0;
        double defenceWeight = 0;

        //PlayerRatingsUtil prUtil = new PlayerRatingsUtil();

        for(Map.Entry<PlayersEntity, PositionsEntity> entry : ts.getPlayerPositions().entrySet()){
            PlayersEntity player = entry.getKey();
            PositionsEntity pos = entry.getValue();

            double atk = player.getRatings().get(pos.getId()).getAttackrating();
            double def = player.getRatings().get(pos.getId()).getDefencerating();
            attackWeight += atk;
            defenceWeight += def;
            //Utils.logger.info(player.getName()+" at "+pos.getPosition()+" rated: "+ Utils.getPosRating(player,pos)+" pos type: "+pos.getPositiontype().getPosition()+" attack rating: "+atk+" def weight: "+def);
        }

        attackCoeff = attackWeight/11;
        defenceCoeff = defenceWeight/11;
    }
}
