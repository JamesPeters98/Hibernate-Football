package algorithms;

import entities.*;
import org.hibernate.Session;
import utils.DBUtil;
import utils.PlayerRatingsUtil;
import utils.RelatedPositions;
import utils.Utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

public class BestTeamSheet implements Callable<BestTeamSheet> {

    private double rating = 0;
    private double weight = 0;
    private List<PlayersEntity> players;
    private List<PositionsEntity> positions;
    private double potentialFactor;
    private HashMap<PlayersEntity, PositionsEntity> playerPosition;
    private FormationsEntity formation;

    private long startTime;
    private long endTime;


    /**
     * Calculates the Best Team sheet given a list of players and a list of positions.
     * @param players list of players to be sorted
     * @param posistions list of positions to be filled
     * @param potentialFactor how much potential should be considered, e.g a factor of 0.5 will add 50% of a players growth to their overall.
     */
    public BestTeamSheet(FormationsEntity formation, List<PlayersEntity> players, List<PositionsEntity> posistions, double potentialFactor) throws NoSuchFieldException, IllegalAccessException {
        //System.out.println("New BestTeamSheet: "+formation.getFormation()+" - pos: "+posistions.toString());
        this.players = players;
        this.positions = posistions;
        this.potentialFactor = potentialFactor;
        this.formation = formation;
        playerPosition = new HashMap<>();
    }

    public double getRating() {
        return rating;
    }

    public double getWeight() {
        return -weight/11;
    }



    private double getWeight(PlayersEntity player, PositionsEntity position) {
        PlayerRatingsEntity ratingsEntity = player.getRating(position.getId());

        double preferredPos = 0.9; //If not preferred position only 90% as effective.
        double potentialWeight = (ratingsEntity.getRating()+(potentialFactor*player.getGrowth()))/(ratingsEntity.getRating());
        double attackingPref = 1;
        double defensivePref = 1;
        if(RelatedPositions.isRelatedPos(player,position)) preferredPos = 1;

        return (-ratingsEntity.getAttackrating()*attackingPref-ratingsEntity.getDefencerating()*defensivePref)*potentialWeight*preferredPos;
    }

    @Override
    public BestTeamSheet call() {
        //startTime = System.currentTimeMillis();
        HashMap<Integer,PlayersEntity> playerMap = new HashMap<>();
        HashMap<Integer,PositionsEntity> positionMap = new HashMap<>();
        double[][] weights = new double[players.size()][positions.size()];

        //Calculate weights for all outfield players
        int i= 0;
        for(PlayersEntity player : players){
            int j = 0;
            playerMap.put(i,player);
            //Check all outfield positions
            for(PositionsEntity position : positions){
                positionMap.put(j,position);
                weights[i][j] = getWeight(player,position);
                j++;
            }
            i++;
        }

        HungarianAlgorithm algorithm = new HungarianAlgorithm(weights);
        int[] result = algorithm.execute();

        for(int p = 0; p < result.length; p++){
            int posIndex = result[p];
            PlayersEntity player = playerMap.get(p);
            if(posIndex == -1){
                continue;
            }
            PositionsEntity pos = positionMap.get(result[p]);
            playerPosition.put(player,pos);
            weight += weights[p][posIndex];
            rating += player.getRating(pos.getId()).getRating();
//            System.out.println(player.getName()
//                    +" playing at "+pos.getPosition()
//                    +" rated: "+Math.round(Utils.getPosRating(player,pos)));
        }


        rating/= positions.size();


        //endTime = System.currentTimeMillis();

        //Utils.logger.debug("Best Team Sheet Time: "+(endTime-startTime)+"ms");

        return this;
    }

    public HashMap<PlayersEntity, PositionsEntity> getPlayerPositions() {
        return playerPosition;
    }

    public FormationsEntity getFormation() {
        return formation;
    }
}
