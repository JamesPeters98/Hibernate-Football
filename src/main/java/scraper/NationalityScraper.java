package scraper;

import entities.NationalityEntity;
import entities.RegionsEntity;
import entities.TeamsEntity;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.CSVWriter;
import utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NationalityScraper extends Scraper {

    HashMap<Integer,RegionsEntity> regions = new HashMap<>();
    HashMap<Integer,NationalityEntity> nationalities = new HashMap<>();
    CSVWriter csv;

    public NationalityScraper(Session session) {
        super(session);
    }

    @Override
    protected void scrape(int page) throws IOException {
        Document doc = Jsoup.connect("https://sofifa.com/teams").get();
        Element select = doc.getElementsByAttributeValueContaining("data-placeholder","Nationality / Region").first(); //This might change if site get reformatted!
        Elements optgroups = select.getElementsByTag("optgroup");

        int regionId = 0;
        for (Element optgroup : optgroups) {
            //Nationality Region
            String region = optgroup.attr("label");

            //Region Entity
            RegionsEntity regionsEntity = new RegionsEntity();
            regionsEntity.setId(regionId);
            regionsEntity.setName(region);
            regions.put(regionsEntity.getId(),regionsEntity);

            for(Element option : optgroup.getElementsByTag("option")) {
                //Nationality
                String nationality = option.text();
                String id = option.attr("value");

                NationalityEntity nationalityEntity = new NationalityEntity();
                nationalityEntity.setId(Integer.parseInt(id));
                nationalityEntity.setName(nationality);
                nationalityEntity.setRegionId(regionId);

                nationalities.put(nationalityEntity.getId(),nationalityEntity);
            }
            regionId++;
        }
    }

    @Override
    public void execute() {
        System.out.println("----------------------------------");
        Utils.logger.info("Loading - "+getClass().getName());
        try {
            scrape(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertToDatabase();
        session.close();
    }

    @Override
    protected double rows() {
        return 0;
    }

    @Override
    protected ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Override
    protected void insertToDatabase() {
        session.beginTransaction();
        regions.forEach((id,regionsEntity) -> session.saveOrUpdate(regionsEntity));
        nationalities.forEach((id,nationalityEntity) -> session.saveOrUpdate(nationalityEntity));
        session.getTransaction().commit();
    }
}
