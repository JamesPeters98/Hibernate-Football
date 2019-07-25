package com.jamesdpeters.algorithms;

//public class PlayerRating implements Callable<PlayerRating> {
//
//    private double rating = 0;
//    private double weight = 0;
//
//    private PlayersEntity player;
//
//
//    static {
//
//    }
//
//    public PlayerRating(PlayersEntity player) throws NoSuchFieldException, IllegalAccessException {
//        this.player = player;
//    }
//
//    public double getRating() {
//        return rating;
//    }
//
//    public double getWeight() {
//        return -weight/11;
//    }
//
//
//
//    private double getWeight(PlayersEntity player, PositionsEntity position) throws NoSuchFieldException, IllegalAccessException {
//        double preferredPos = -1;
//        double potentialWeight = player.getGrowth()*potentialFactor;
//        if(RelatedPositions.isRelatedPos(player,position)) preferredPos = 1;
//        return -Utils.getPosRating(player,position)-preferredPos-potentialWeight;
//    }
//
//    @Override
//    public PlayerRating call() throws Exception {
//        HashMap<Integer,PlayersEntity> playerMap = new HashMap<>();
//        HashMap<Integer,PositionsEntity> positionMap = new HashMap<>();
//        double[][] weights = new double[players.size()][positions.size()];
//
//        //Calculate weights for all outfield players
//        int i= 0;
//        for(PlayersEntity player : players){
//            int j = 0;
//            playerMap.put(i,player);
//            //Check all outfield positions
//            for(PositionsEntity position : positions){
//                positionMap.put(j,position);
//                weights[i][j] = getWeight(player,position);
//                j++;
//            }
//            i++;
//        }
//
//        HungarianAlgorithm algorithm = new HungarianAlgorithm(weights);
//        int[] result = algorithm.execute();
//
//        for(int p = 0; p < result.length; p++){
//            int posIndex = result[p];
//            PlayersEntity player = playerMap.get(p);
//            if(posIndex == -1){
//                continue;
//            }
//            PositionsEntity pos = positionMap.get(result[p]);
//            playerPosition.put(player,pos);
//            weight += weights[p][posIndex];
//            rating += getPosRating(player,pos);
////            System.out.println(player.getName()
////                    +" playing at "+pos.getPosition()
////                    +" rated: "+Math.round(getPosRating(player,pos)));
//        }
//
//
//        rating/= positions.size();
//
//        return this;
//    }
//
//    public HashMap<PlayersEntity, PositionsEntity> getPlayerPositions() {
//        return playerPosition;
//    }
//
//    public FormationsEntity getFormation() {
//        return formation;
//    }
//}
