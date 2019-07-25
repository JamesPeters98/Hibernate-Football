package com.jamesdpeters;

import com.jamesdpeters.entities.PlayerStatsEntity;
import com.jamesdpeters.entities.PlayersEntity;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerScraper extends Scraper {

//    List<PlayersEntity> players = new ArrayList<>();
//    List<PlayerStatsEntity> playerStats = new ArrayList<>();

    HashMap<Integer,PlayersEntity> playerMap = new HashMap<>();
    HashMap<Integer,PlayerStatsEntity> playerStatsMap = new HashMap<>();

    public PlayerScraper(Session session) {
        super(session);
    }

    @Override
    protected void scrape(int page) {
        //System.out.println("Scraping Players page: "+page);
        String url = "https://sofifa.com/players?col=oa&sort=desc&showCol%5B%5D=pi&showCol%5B%5D=ae&showCol%5B%5D=oa&showCol%5B%5D=gu&showCol%5B%5D=cr&showCol%5B%5D=fi&showCol%5B%5D=he&showCol%5B%5D=sh&showCol%5B%5D=vo&showCol%5B%5D=dr&showCol%5B%5D=cu&showCol%5B%5D=fr&showCol%5B%5D=lo&showCol%5B%5D=bl&showCol%5B%5D=ac&showCol%5B%5D=sp&showCol%5B%5D=ag&showCol%5B%5D=re&showCol%5B%5D=ba&showCol%5B%5D=so&showCol%5B%5D=ju&showCol%5B%5D=st&showCol%5B%5D=sr&showCol%5B%5D=ln&showCol%5B%5D=ar&showCol%5B%5D=in&showCol%5B%5D=po&showCol%5B%5D=vi&showCol%5B%5D=pe&showCol%5B%5D=cm&showCol%5B%5D=ma&showCol%5B%5D=sa&showCol%5B%5D=sl&showCol%5B%5D=gd&showCol%5B%5D=gh&showCol%5B%5D=gk&showCol%5B%5D=gp&showCol%5B%5D=gr&showCol%5B%5D=tt&showCol%5B%5D=sk&offset="+offset(page);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element body = doc.getElementsByTag("tbody").get(0);
        Element header = doc.getElementsByTag("thead").get(0);

        Elements trBody = body.getElementsByTag("tr");
        Element trHeader = header.getElementsByTag("tr").get(1);

        Elements tdHeader = trHeader.getElementsByTag("th");

        //System.out.println("rows: "+trBody.size());
        for (Element rowBody : trBody) {
            Elements tdBody = rowBody.getElementsByTag("td");

            PlayersEntity player = new PlayersEntity();
            PlayerStatsEntity statsEntity = new PlayerStatsEntity();

            try {

                String photo = tdBody.get(0).getElementsByTag("img").get(0).attr("data-src");
                int nationality = Integer.parseInt(tdBody.get(1).getElementsByTag("a").get(0).attr("href").split("na=")[1]);
                String name = tdBody.get(1).getElementsByTag("a").get(1).text();
                int posId = Integer.parseInt(tdBody.get(1).getElementsByTag("a").get(2).attr("href").split("=")[1]);
                int age = Integer.parseInt(tdBody.get(2).text());
                int overall = Integer.parseInt(tdBody.get(3).child(0).text());
                String[] teamInfo = tdBody.get(4).getElementsByTag("a").get(0).attr("href").split("/");
                int teamId = 0;
                if (teamInfo.length >= 3) {
                    teamId = Integer.parseInt(tdBody.get(4).getElementsByTag("a").get(0).attr("href").split("/")[2]);
                }
                int id = Integer.parseInt(tdBody.get(6).text());
                int growth = Integer.parseInt(tdBody.get(7).child(0).text());

                player.setId(id);
                player.setName(name);
                player.setAge(age);
                player.setGrowth(growth);
                player.setOverall(overall);
                player.setPhoto(photo);
                player.setTeamid(teamId);
                player.setPositionId(posId);
                player.setNationalityId(nationality);


            //System.out.println(name  + "," + age + "," + overall + "," + teamId + "," + growth);

            int offset = 9;

            HashMap<String, Integer> stats = new HashMap<>();

            //ATTACKING
            HashMap<String, Integer> attacking = new HashMap<>();
            insertColumns(stats, offset, 5, tdHeader, tdBody);

            //SKILLS
            HashMap<String, Integer> skills = new HashMap<>();
            insertColumns(stats, offset + 6, 5, tdHeader, tdBody);

            //MOVEMENT
            HashMap<String, Integer> movement = new HashMap<>();
            insertColumns(stats, offset + 12, 5, tdHeader, tdBody);

            //POWER
            HashMap<String, Integer> power = new HashMap<>();
            insertColumns(stats, offset + 18, 5, tdHeader, tdBody);

            //MENTALITY
            HashMap<String, Integer> mentality = new HashMap<>();
            insertColumns(stats, offset + 24, 6, tdHeader, tdBody);

            //DEFENDING
            HashMap<String, Integer> defending = new HashMap<>();
            insertColumns(stats, offset + 31, 3, tdHeader, tdBody);

            //GOALKEEPER
            HashMap<String, Integer> goalkeeper = new HashMap<>();
            insertColumns(stats, offset + 35, 5, tdHeader, tdBody);


            statsEntity.setId(player.getId());

            statsEntity.setAcceleration(stats.get("Acceleration"));
            statsEntity.setAggression(stats.get("Aggression"));
            statsEntity.setAgility(stats.get("Agility"));
            statsEntity.setBalance(stats.get("Balance"));
            statsEntity.setBallControl(stats.get("Ball Control"));
            statsEntity.setComposure(stats.get("Composure"));
            statsEntity.setCrossing(stats.get("Crossing"));
            statsEntity.setCurve(stats.get("Curve"));
            statsEntity.setDribbling(stats.get("Dribbling"));
            statsEntity.setFinishing(stats.get("Finishing"));
            statsEntity.setFkAccuracy(stats.get("FK Accuracy"));
            statsEntity.setGkDiving(stats.get("GK Diving"));
            statsEntity.setGkHandling(stats.get("GK Handling"));
            statsEntity.setGkKicking(stats.get("GK Kicking"));
            statsEntity.setGkPositioning(stats.get("GK Positioning"));
            statsEntity.setGkReflexes(stats.get("GK Reflexes"));
            statsEntity.setHeadingAccuracy(stats.get("Heading Accuracy"));
            statsEntity.setInterceptions(stats.get("Interceptions"));
            statsEntity.setJumping(stats.get("Jumping"));
            statsEntity.setLongPassing(stats.get("Long Passing"));
            statsEntity.setLongShots(stats.get("Long Shots"));
            statsEntity.setMarking(stats.get("Marking"));
            statsEntity.setPenalties(stats.get("Penalties"));
            statsEntity.setPosistioning(stats.get("Positioning"));
            statsEntity.setReactions(stats.get("Reactions"));
            statsEntity.setShortPassing(stats.get("Short Passing"));
            statsEntity.setShotPower(stats.get("Shot Power"));
            statsEntity.setSlidingTackle(stats.get("Sliding Tackle"));
            statsEntity.setSprintSpeed(stats.get("Sprint Speed"));
            statsEntity.setStamina(stats.get("Stamina"));
            statsEntity.setStandingTackle(stats.get("Standing Tackle"));
            statsEntity.setStrength(stats.get("Strength"));
            statsEntity.setVision(stats.get("Vision"));
            statsEntity.setVolleys(stats.get("Volleys"));

            addToMap(playerStatsMap,player.getId(),statsEntity);
            addToMap(playerMap,player.getId(),player);

            } catch (Exception e){
                e.printStackTrace();
            }

//            playerStats.add(statsEntity);
//            players.add(player);
//            PlayersEntity oldPlayer = addToMap(playerMap,player.getId(),player);
//            if(oldPlayer!=null){
//                Utils.logger.error("Player already in Map! "+player.getId()+":"+player.getName()+" ovr: "+player.getOverall());
//                Utils.logger.error("Old player: "+player.getId()+":"+oldPlayer.getName()+" ovr: "+oldPlayer.getOverall());
//                System.out.println("page: "+page+": "+url);
//            }
        }

    }

    @Override
    protected double rows() {
        return 18156;
        //return 120;
    }

    @Override
    protected ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Override
    protected void insertToDatabase() {
        System.out.println("Player size: "+playerMap.size());

        session.beginTransaction();
        playerStatsMap.forEach((key,entity)-> {
            session.saveOrUpdate(entity);
        });
        session.getTransaction().commit();

        session.beginTransaction();
        playerMap.forEach((key,entity)-> {
            session.saveOrUpdate(entity);
        });
        session.getTransaction().commit();
    }


    private void insertColumns(HashMap<String, Integer> map, int startColumn, int columns, Elements tdHeader, Elements tdBody){
        for(int c=0; c<columns; c++){
            String title = tdHeader.get(startColumn+c).attr("title");
            int stat = Integer.parseInt(tdBody.get(startColumn+c).child(0).text());
            map.put(title,stat);
            //System.out.println(title+":"+stat);
        }
    }


}
