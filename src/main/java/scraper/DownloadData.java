package scraper;

import Exceptions.NoDatabaseSelectedException;
import entities.NationalityEntity;
import utils.SessionStore;
import workers.CalculatePlayerRatings;

import java.util.concurrent.ExecutionException;

public class DownloadData {

    public static void main(String[] args) throws NoDatabaseSelectedException, ExecutionException, InterruptedException {
        SessionStore.setDB("GameSave");
        scrape();
    }

    public static void scrape() throws NoDatabaseSelectedException, ExecutionException, InterruptedException {
        NationalityScraper nationalityScraper = new NationalityScraper(SessionStore.getSession());
        nationalityScraper.execute();

        LeagueScraper leagueScraper = new LeagueScraper(SessionStore.getSession());
        leagueScraper.execute();

        TeamScraper teamScraper = new TeamScraper(SessionStore.getSession());
        teamScraper.execute();

        PlayerScraper playerScraper = new PlayerScraper(SessionStore.getSession());
        playerScraper.execute();

        new CalculatePlayerRatings();
    }
}
