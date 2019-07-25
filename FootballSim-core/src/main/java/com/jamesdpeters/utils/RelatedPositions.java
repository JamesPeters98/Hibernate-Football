package com.jamesdpeters.utils;

import com.jamesdpeters.entities.PlayersEntity;
import com.jamesdpeters.entities.PositionsEntity;

import java.util.HashMap;
import java.util.List;

public class RelatedPositions {

    private static HashMap<Integer, List<Integer>> relatedPos = new HashMap<>();

    static {
//        relatedPos.put(1, Arrays.asList(3,2));
//        relatedPos.put(2, Arrays.asList(1,3));
//        relatedPos.put(3, Arrays.asList(1,2));
//        relatedPos.put(4, Arrays.asList(5,2,6));
//        relatedPos.put(5, Arrays.asList(2,6,4,7));
//        relatedPos.put(6, Arrays.asList(3,8,9,5));
//        relatedPos.put(7, Arrays.asList(5,10,6));
//        relatedPos.put(8, Arrays.asList(6,9,3));
//        relatedPos.put(9, Arrays.asList(10,8,6,9,3));
//        relatedPos.put(10, Arrays.asList(7,9,5));
//        relatedPos.put(11, new ArrayList<>());
    }

    public static boolean isRelatedPos(PlayersEntity player, PositionsEntity pos){
        if(player.getPosition().getId() == pos.getId()) return true;
        if(relatedPos.get(player.getPositionId()) == null) return false;
        if(relatedPos.get(player.getPositionId()).contains(pos.getId())) return true;
        else return false;
    }



}
