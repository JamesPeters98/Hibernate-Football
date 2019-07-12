package scraper;

import org.hibernate.Session;
import utils.SessionStore;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Scraper {

    private ExecutorService executor;
    private int executions = 0;

    protected Session session;

    public Scraper(Session session){
        this.session = session;
    }

    protected abstract void scrape(int page) throws IOException;
    protected abstract double rows();
    protected abstract ExecutorService getExecutorService();
    protected abstract void insertToDatabase();

    protected double rowsPerPage(){
        return 60;
    }

    int offset(int n){
        return (int) (rowsPerPage()*n);
    }

    int getLimit(){
        return (int) Math.ceil(rows()/rowsPerPage());
    }

    public void execute(){
        executor = getExecutorService();

        int limit = getLimit();
        //System.out.println("Limit: "+limit);

        System.out.println("----------------------------------");
        Utils.logger.info("Loading - "+getClass().getName());
        List<Callable<Object>> callables = new ArrayList<>();
        for(int n=0; n<limit; n++){
            ScraperCallable callable = new ScraperCallable(this,n) {
                @Override
                void execute() {
                    try {
                        scrape(pageNumber);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            callables.add(callable);
        }

        try {
            executor.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        insertToDatabase();
        session.close();
        executor.shutdown();
    }

    public void addToExecutedPages(){
        executions++;
    }

    public int getExecutions(){
        return executions;
    }

    protected <T, E> E addToMap(HashMap<T, E> map, T key, E value){
        if(map.containsKey(key)){
            return map.get(key);
        } else {
            map.put(key,value);
            return null;
        }
    }

}
