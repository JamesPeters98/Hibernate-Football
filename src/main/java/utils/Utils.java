package utils;

import entities.PlayerStatsEntity;
import entities.PlayersEntity;
import entities.PositionsEntity;
import entities.PositiontypeEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.hibernate.Session;

import java.lang.reflect.Field;
import java.util.List;

public class Utils {

    static {
        setLoggerLevel(Level.DEBUG);
    }

    public static Logger logger = LogManager.getLogger(Utils.class);

    public static void setLoggerLevel(Level level){
        Configurator.setLevel(Utils.class.getCanonicalName(),level);
    }


}
