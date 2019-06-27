package scraper;

import utils.SessionStore;

public class Scrape {

    public static void main(String[] args) {
        SessionStore.setDB("TEST");
        LeagueScraper leagueScraper = new LeagueScraper(SessionStore.getSession());
        leagueScraper.execute();

        TeamScraper teamScraper = new TeamScraper(SessionStore.getSession());
        teamScraper.execute();

        PlayerScraper playerScraper = new PlayerScraper(SessionStore.getSession());
        playerScraper.execute();
    }
}
