package com.jamesdpeters.listeners;

public abstract class ProgressListener {

    private int total;
    private int progress;

    public void addToTotal(int toAdd){
        total += toAdd;
    }

    public void progress(int toAdd){
        progress += toAdd;
        onProgressUpdate(progress,total);
    }

    public abstract void onProgressUpdate(int progress, int totalSize);
}
