package com.jamesdpeters.scraper;

import com.jamesdpeters.entities.LeaguesEntity;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.jamesdpeters.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LeagueScraper extends Scraper {

    List<LeaguesEntity> leaguesEntities = new ArrayList<>();

    public LeagueScraper(Session session) {
        super(session);
    }

    @Override
    protected void scrape(int page) throws IOException {
        System.out.println("LeagueScraper: Scraping page: "+page);
        Document doc = Jsoup.connect("https://sofifa.com/leagues").get();
        Element body = doc.getElementsByTag("tbody").get(0);
        Elements trBody = body.getElementsByTag("tr");


        for (Element rowBody : trBody) {
            Elements tdBody = rowBody.getElementsByTag("td");
            String leagueImage = tdBody.get(0).getElementsByTag("img").attr("data-src");
            int id = Integer.parseInt(tdBody.get(1).getElementsByTag("a").attr("href").split("/league/")[1]);
            String[] league = tdBody.get(1).getElementsByTag("a").text().split(" \\(");
            String leagueName = league[0];
            int division = 0;
            if(league.length > 1) division = Integer.parseInt(league[1].replaceFirst("\\)",""));

            LeaguesEntity leaguesEntity = new LeaguesEntity();
            leaguesEntity.setId(id);
            leaguesEntity.setDivision(division);
            leaguesEntity.setLeaguename(leagueName);

            leaguesEntities.add(leaguesEntity);
        }

    }

    @Override
    protected double rows() {
        return 1;
    }

    @Override
    protected double rowsPerPage() {
        return 1;
    }

    @Override
    protected ExecutorService getExecutorService() {
        return Executors.newSingleThreadExecutor();
    }

    @Override
    protected void insertToDatabase() {
        session.beginTransaction();
        leaguesEntities.forEach((leaguesEntity -> session.saveOrUpdate(leaguesEntity)));
        session.getTransaction().commit();
    }
}
