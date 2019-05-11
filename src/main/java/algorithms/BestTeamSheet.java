package algorithms;

import entities.FormationsEntity;
import entities.PlayerStatsEntity;
import entities.PlayersEntity;
import entities.PositionsEntity;
import utils.DBUtil;
import utils.RelatedPositions;

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

    /**
     * Calculates the Best Team sheet given a list of players and a list of positions.
     * @param players list of players to be sorted
     * @param posistions list of positions to be filled
     * @param potentialFactor how much potential should be considered, e.g a factor of 0.5 will add 50% of a players growth to their overall.
     */
    public BestTeamSheet(FormationsEntity formation, List<PlayersEntity> players, List<PositionsEntity> posistions, double potentialFactor) throws NoSuchFieldException, IllegalAccessException {
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

    private double getPosRating(PlayersEntity player, PositionsEntity pos) throws NoSuchFieldException, IllegalAccessException {
        PlayerStatsEntity stats = player.getStats();

        double overall = 0;
        for(Field field : stats.getClass().getDeclaredFields()){
            if(field.getName() == "id") continue;

            field.setAccessible(true);
            Field posF = pos.getClass().getDeclaredField(field.getName());
            posF.setAccessible(true);

            int stat = 0;
            double coef = 0;

            Object coefO = posF.get(pos);
            Object statO = field.get(stats);

            if(coefO != null) coef = (double) coefO;
            if(statO != null) stat = (int) statO;

            overall += stat*coef;
        }

        return overall;
    }

    private double getWeight(PlayersEntity player, PositionsEntity position) throws NoSuchFieldException, IllegalAccessException {
        double preferredPos = -1;
        double potentialWeight = player.getGrowth()*potentialFactor;
        if(RelatedPositions.isRelatedPos(player,position)) preferredPos = 1;
        return -getPosRating(player,position)-preferredPos-potentialWeight;
    }

    @Override
    public BestTeamSheet call() throws Exception {
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
            rating += getPosRating(player,pos);
//            System.out.println(player.getName()
//                    +" playing at "+pos.getPosition()
//                    +" rated: "+Math.round(getPosRating(player,pos)));
        }


        rating/= positions.size();

        return this;
    }

    public HashMap<PlayersEntity, PositionsEntity> getPlayerPositions() {
        return playerPosition;
    }

    public FormationsEntity getFormation() {
        return formation;
    }
}
