package scraper;

import entities.TeamsEntity;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.CSVWriter;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TeamScraper extends Scraper {

    HashMap<Integer,TeamsEntity> teams = new HashMap<>();
    CSVWriter csv;

    public TeamScraper(Session session) {
        super(session);
        csv = new CSVWriter("TeamRatings_Actual");
        csv.setHeader("Team","Rating","Attack Rating","Midfield Rating","Defence Rating");
        teams.put(0,getDefaultTeam());
    }

    @Override
    protected void scrape(int page) throws IOException {
        //Utils.logger.info("TeamScraper: Scraping page: "+page);
        Document doc = Jsoup.connect("https://sofifa.com/teams?showCol%5B0%5D=ti&showCol%5B1%5D=oa&showCol%5B2%5D=at&showCol%5B3%5D=md&showCol%5B4%5D=df&showCol%5B5%5D=tb&showCol%5B6%5D=ps&col=ti&sort=asc&offset="+offset(page)).get();
        Element body = doc.getElementsByTag("tbody").get(0);
        Elements trBody = body.getElementsByTag("tr");

        for (Element rowBody : trBody) {
            Elements tdBody = rowBody.getElementsByTag("td");

            try {

                //String teamImage = tdBody.get(0).getElementsByTag("img").attr("data-src");
                Elements teamInfo = tdBody.get(1).getElementsByTag("div").get(0).getElementsByTag("a");
                //String countryId = teamInfo.get(0).attr("href").split("na=")[1];
                String teamName = teamInfo.get(1).text();
                int leagueId = Integer.parseInt(tdBody.get(1).getElementsByAttributeValueContaining("href", "/teams?lg=").get(0).attr("href").split("lg=")[1]);
                int id = Integer.parseInt(tdBody.get(2).text());
                int overall = Integer.parseInt(tdBody.get(3).text());
                int attack = Integer.parseInt(tdBody.get(4).text());
                int mid = Integer.parseInt(tdBody.get(5).text());
                int def = Integer.parseInt(tdBody.get(6).text());
                Utils.logger.debug(overall+","+attack+","+mid+","+def);
                csv.addRow(teamName,overall,attack,mid,def);

                TeamsEntity team = new TeamsEntity();
                team.setId(id);
                team.setName(teamName);
                team.setLeagueid(leagueId);

                addToMap(teams,team.getId(),team);
            } catch (Exception e){
                e.printStackTrace();
            }

//            LeaguesEntity entity = new LeaguesEntity();
        }

        //Utils.logger.info("TeamScraper: page: "+page+" teams: "+teams.size());
    }

    @Override
    protected double rows() {
        return 698;
    }

    @Override
    protected ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Override
    protected void insertToDatabase() {
        try {
            csv.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.beginTransaction();
        teams.forEach((id,teamsEntity) -> session.saveOrUpdate(teamsEntity));
        session.getTransaction().commit();
    }

    private TeamsEntity getDefaultTeam(){
        TeamsEntity team = new TeamsEntity();
        team.setId(0);
        team.setLeagueid(76);
        team.setName("Free Agents");
        return team;
    }
}
