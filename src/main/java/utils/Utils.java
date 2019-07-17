package utils;

import entities.*;
import frameworks.Team;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.hibernate.Session;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {

    static {
        setLoggerLevel(Level.DEBUG);
    }

    public static Logger logger = LogManager.getLogger(Utils.class);

    public static void setLoggerLevel(Level level){
        Configurator.setLevel(Utils.class.getCanonicalName(),level);
    }

    public static int poisson(double a) {
        Random random = new Random();
        double limit = Math.exp(-a), prod = random.nextDouble();
        int n;
        for (n = 0; prod >= limit; n++)
            prod *= random.nextDouble();
        return n;
    }

    public static <T> String toCommaList(T[] a){
        StringBuilder list = new StringBuilder();
        for(T o : a) list.append(o).append(",");
        list.deleteCharAt(list.length()-1);
        return list.toString();
    }

    public static <T> String toCommaList(List<T> a){
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
}
