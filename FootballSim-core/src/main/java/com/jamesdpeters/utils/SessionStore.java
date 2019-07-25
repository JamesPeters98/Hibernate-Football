package com.jamesdpeters.utils;

import com.jamesdpeters.exceptions.NoDatabaseSelectedException;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;
import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public static void closeAllRemainingSessions() {
        listOfSessions.forEach(Session::close);
        ourSessionFactory.close();
    }

    public static Session getSession() {
        listOfSessions.removeIf(session -> !session.isOpen());
        //System.out.println("Open Sessions: "+listOfSessions.size());

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
        copyDefaultDB();
    }

    private static void checkDbFile(String dbName){
        File dbFile = new File(getDbPath(DB_NAME)+".mv.db");
        if(!dbFile.isFile()){
            Utils.logger.info("DB: "+dbName+" did not exist! Copying default DB!");
            InputStream defaultFile = getDefaultDb();
            try {
                FileUtils.copyInputStreamToFile(defaultFile,dbFile);
                //FileUtils.copyFile(defaultFile,dbFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            copyDefaultDB();
        }
    }

    private static void copyDefaultDB(){
        InputStream defaultZipFile = getDefaultDb();
        try {
            ZipUtil.unzipDB(new File(getDbPath("")),defaultZipFile, getDefaultDbName(),DB_NAME+".mv.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getDbPath(String dbname){
        AppDirs dir = AppDirsFactory.getInstance();
        String saveDir = dir.getUserDataDir("FootballSim","1.0","James");
        return saveDir+"/"+dbname;
    }

    private static String getDefaultDbZipName(){
        String fileName;
        if(USE_FILLED){
            fileName = "default-filled.mv.zip";
        } else {
            fileName = "default.mv.zip";
        }
        return fileName;
    }

    private static String getDefaultDbName(){
        String fileName;
        if(USE_FILLED){
            fileName = "default-filled.mv.db";
        } else {
            fileName = "default.mv.db";
        }
        return fileName;
    }

    private static InputStream getDefaultDb(){
        return SessionStore.class.getClassLoader().getResourceAsStream("db/"+ getDefaultDbZipName());
    }

    public static void useEmptyDefault(){
        USE_FILLED = false;
    }

    public static void useFilledDefault(){
        USE_FILLED = true;
    }

}
