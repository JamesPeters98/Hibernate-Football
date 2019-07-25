package utils;

import Exceptions.NoDatabaseSelectedException;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;
import org.apache.commons.io.FileUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SessionStore {

    private static SessionFactory ourSessionFactory;

    private static String DB_NAME;
    private static Configuration configuration;

    private static boolean USE_FILLED = true;

    private static List<Session> listOfSessions = new ArrayList<>();

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

    public static List<Session> getListOfOpenSessions(){
        return listOfSessions;
    }

    public static void closeAllRemainingSessions(){
        listOfSessions.forEach(Session::close);
        ourSessionFactory.close();
    }

    public static Session getSession() {
        listOfSessions.removeIf(session -> !session.isOpen());
        //listOfSessions.forEach(session -> session.getStatistics().getCollectionKeys().forEach(o -> System.out.println(o.toString())));
        //listOfSessions.forEach(session -> session.get);
        System.out.println("Open Sessions: "+listOfSessions.size());


        if(DB_NAME == null) try {
            throw new NoDatabaseSelectedException();
        } catch (NoDatabaseSelectedException e) {
            e.printStackTrace();
        }
        Session session = ourSessionFactory.openSession();
        listOfSessions.add(session);
        return session;
    }

    public static Configuration getConfiguration(){
        return configuration;
    }

    public static void resetDB(){
        closeAllRemainingSessions();
        copyDefaultDB(DB_NAME);
    }

    private static void checkDbFile(String dbName){
        File dbFile = new File(getDbPath(DB_NAME)+".mv.db");
        if(!dbFile.isFile()){
            Utils.logger.info("DB: "+dbName+" did not exist! Copying default DB!");
            copyDefaultDB(dbFile);
        }
    }

    private static void copyDefaultDB(String dbFileName){
        copyDefaultDB(new File(getDbPath(DB_NAME)+".mv.db"));
    }

    private static void copyDefaultDB(File dbFile){
        InputStream defaultFile = getDefaultDb();
        try {
            FileUtils.copyInputStreamToFile(defaultFile,dbFile);
            //FileUtils.copyFile(defaultFile,dbFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getDbPath(String dbname){
        AppDirs dir = AppDirsFactory.getInstance();
        String saveDir = dir.getUserDataDir("FootballSim","1.0","James");
        return saveDir+"/"+dbname;
    }

    private static InputStream getDefaultDb(){
        String fileName;

        if(USE_FILLED){
            fileName = "db/default-filled.mv.db";
        } else {
            fileName = "db/default.mv.db";
        }

        return SessionStore.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static void useEmptyDefault(){
        USE_FILLED = false;
    }

    public static void useFilledDefault(){
        USE_FILLED = true;
    }

}
