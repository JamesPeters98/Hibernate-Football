package utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionStore {

    private static final SessionFactory ourSessionFactory;

    static String resourcePath;

    private static SessionStore sessionStore;

    private SessionStore(){

    }

    static {
        try {
            sessionStore = new SessionStore();
            resourcePath = sessionStore.getClass().getResource("../").getPath();
            System.out.println("Resource Path: "+resourcePath);
            Configuration configuration = new Configuration();
            //configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
            //configuration.setProperty("connection.url","jdbc:h2:file:C:/Users/James/IdeaProjects/test");
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

}
