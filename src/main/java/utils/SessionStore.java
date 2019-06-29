package utils;

import Exceptions.NoDatabaseSelectedException;
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

    public static void setDB(String dbName){
        DB_NAME = dbName;
        configure();
    }

    private static void configure(){
        checkDbFile(DB_NAME);
        try {
            configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url","jdbc:h2:file:./db/"+DB_NAME);
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws NoDatabaseSelectedException {
        if(DB_NAME == null) throw new NoDatabaseSelectedException();
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
        File dbFile = new File("db/"+dbName+".mv.db");
        if(!dbFile.isFile()){
            Utils.logger.info("DB: "+dbName+" did not exist! Copying default DB!");
            File defaultFile = new File("db/default.mv.db");
            try {
                FileUtils.copyFile(defaultFile,dbFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
