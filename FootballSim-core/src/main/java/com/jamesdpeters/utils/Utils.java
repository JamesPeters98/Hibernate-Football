package com.jamesdpeters.utils;

import com.jamesdpeters.entities.TeamsEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;

public class Utils {

//    static {
//        setLoggerLevel(Level.DEBUG);
//    }
//
//    public static Logger logger = LogManager.getLogger(Utils.class);
//
//    public static void setLoggerLevel(Level level){
//        Configurator.setLevel(Utils.class.getCanonicalName(),level);
//    }

    public static int poisson(double a) {
        Random random = new Random();
        double limit = Math.exp(-a), prod = random.nextDouble();
        int n;
        for (n = 0; prod >= limit; n++)
            prod *= random.nextDouble();
        return n;
    }

//    public static <T> String toCommaList(T[] a){
//        StringBuilder list = new StringBuilder();
//        for(T o : a) list.append(o).append(",");
//        list.deleteCharAt(list.length()-1);
//        return list.toString();
//    }

    public static <T> String toCommaList(List<T> a){
        if(a.size() == 1) return a.get(0).toString();
        StringBuilder list = new StringBuilder();
        for(T o : a) list.append(o).append(",");
        list.deleteCharAt(list.length()-1);
        return list.toString();
    }

    public static void printPaddedMatchResult(TeamsEntity home, TeamsEntity away, int homeGoals, int awayGoals, int padding){
        String left = StringUtils.leftPad(home.getName()+" "+homeGoals,padding);
        String right = StringUtils.rightPad(awayGoals+" "+away.getName(), padding);
        System.out.println(left+" - "+right);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
