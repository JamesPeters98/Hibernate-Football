package utils;

import Exceptions.NoDatabaseSelectedException;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;
import org.apache.commons.io.FileUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SessionStore {

    private static SessionFactory ourSessionFactory;

    private static String DB_NAME;
    private static Configuration configuration;

    private static boolean USE_FILLED = true;

    public static void setDB(String dbName){
        DB_NAME = dbName;
        configure();
    }

    private static void configure(){
        checkDbFile(DB_NAME);
        try {
            configuration = new Configuration();

            String dbPath = getDbPath(DB_NAME);
            System.out.println(dbPath);
            configuration.setProperty("hibernate.connection.url","jdbc:h2:file:"+dbPath);
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        if(DB_NAME == null) try {
            throw new NoDatabaseSelectedException();
        } catch (NoDatabaseSelectedException e) {
            e.printStackTrace();
        }
        Session session = ourSessionFactory.openSession();
//        try {
//            Utils.logger.debug("Opening DB connection: "+ourSessionFactory.getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection().getMetaData().getURL());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return session;
    }

    public static Configuration getConfiguration(){
        return configuration;
    }

    private static void checkDbFile(String dbName){
        File dbFile = new File(getDbPath(DB_NAME)+".mv.db");
        if(!dbFile.isFile()){
            Utils.logger.info("DB: "+dbName+" did not exist! Copying default DB!");
            File defaultFile = new File(getDefaultDbPath());
            try {
                FileUtils.copyFile(defaultFile,dbFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getDbPath(String dbname){
        AppDirs dir = AppDirsFactory.getInstance();
        String saveDir = dir.getUserDataDir("FootballSim","1.0","James");
        return saveDir+"/"+dbname;
    }

    private static String getDefaultDbPath(){
        if(USE_FILLED){
            return "db/default-filled.mv.db";
        } else {
            return "db/default.mv.db";
        }
    }

    public static void useEmptyDefault(){
        USE_FILLED = false;
    }

    public static void useFilledDefault(){
        USE_FILLED = true;
    }

}
