package com.jamesdpeters.scraper;

import com.jamesdpeters.entities.LeaguesEntity;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.jamesdpeters.utils.SessionStore;
import com.jamesdpeters.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LeagueCountryScraper extends Scraper {

    List<LeaguesEntity> leaguesEntities;

    public static void main(String[] args) {
        SessionStore.setDB("default-filled");
        new LeagueCountryScraper(SessionStore.getSession()).execute();
    }

    public LeagueCountryScraper(Session session) {
        super(session);
        leaguesEntities = session.createQuery("from LeaguesEntity", LeaguesEntity.class).list();
        if(leaguesEntities.size() == 0) System.err.println("League Entity Scraper class needs to be run first! ");
    }

    @Override
    protected void scrape(int page) throws IOException {
        System.out.println("LeagueCountryScraper: Scraping page: "+page);

        LeaguesEntity league = leaguesEntities.get(page);

        Document doc = Jsoup.connect("https://sofifa.com/league/"+league.getId()).get();
        Element li = doc.getElementsByTag("li").get(0);
        Element a = li.getElementsByTag("a").get(0);

        String id = a.attr("href").split("na=")[1];
        System.out.println(league.getLeaguename()+" countryID: "+id);

        league.setCountryId(Integer.parseInt(id));

    }

    @Override
    protected double rows() {
        return leaguesEntities.size();
    }

    @Override
    protected double rowsPerPage() {
        return 1;
    }

    @Override
    protected ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Override
    protected void insertToDatabase() {
        session.beginTransaction();
        leaguesEntities.forEach((leaguesEntity -> session.saveOrUpdate(leaguesEntity)));
        session.getTransaction().commit();
    }
}
