package com.jamesdpeters.scraper;

import com.jamesdpeters.utils.Utils;

import java.io.IOException;
import java.util.concurrent.Callable;

public abstract class ScraperCallable implements Callable {

    public final int pageNumber;
    private Scraper scraper;


    public ScraperCallable(Scraper scraper, int page){
        this.pageNumber = page;
        this.scraper = scraper;
    }


    abstract void execute() throws IOException;

    @Override
    public Object call() {
        try {
            execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scraper.addToExecutedPages();
        System.out.println(scraper.getClass().getName()+": Scraped page: "+pageNumber+" "+getPercentage()+"% ("+scraper.getExecutions()+"/"+scraper.getLimit()+")");
        return null;
    }

    private double getPercentage(){
        return Math.round(1000000*(double)scraper.getExecutions()/(double)scraper.getLimit())/10000;
    }
}
