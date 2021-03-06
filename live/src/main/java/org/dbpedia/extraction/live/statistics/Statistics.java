package org.dbpedia.extraction.live.statistics;

import org.slf4j.Logger;
import org.dbpedia.extraction.live.main.Main;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created with IntelliJ IDEA.
 * User: Dimitris Kontokostas
 * Date: 8/3/12
 * Time: 11:30 AM
 * Handles all statistics
 */
public class Statistics {
    private static Logger logger = LoggerFactory.getLogger(Statistics.class);
    // Number of queued/extracted items
    public static final int numItems = 20;
    // Update interval in miliseconds
    private final long statisticsUpdateInterval;
    // Initial delay on application startup
    private final long statisticsInitialDelay;
    private Timer timer = new Timer("DBpedia-Live Statistics Timer");

    public Statistics(long updateInterval, long initialDelay) {
        this.statisticsUpdateInterval = updateInterval;
        this.statisticsInitialDelay = initialDelay;
    }

    public void stopStatistics() {
        // TODO wait to finish ?
        timer.cancel();
    }


    public void startStatistics() {
        // TODO read old statistics
        // TODO write Statistics dir / file if not exists
        // TODO init StatisticsData variables

        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    StatisticsData.generateStatistics();
                } catch (Exception exp) {
                    logger.error("DBpedia-live Statistics: I/O Error: " + exp.getMessage(), exp);
                }
            }
        }, statisticsInitialDelay, statisticsUpdateInterval); //One-Minute

    }
}
