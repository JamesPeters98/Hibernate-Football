package com.jamesdpeters;

import com.jamesdpeters.exceptions.NoDatabaseSelectedException;
import com.jamesdpeters.utils.SessionStore;
import com.jamesdpeters.workers.CalculatePlayerRatings;

import java.util.concurrent.ExecutionException;

public class DownloadData {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SessionStore.useEmptyDefault();
        SessionStore.setDB("default-filled");
        scrape();
    }

    private static void scrape() throws ExecutionException, InterruptedException {
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
