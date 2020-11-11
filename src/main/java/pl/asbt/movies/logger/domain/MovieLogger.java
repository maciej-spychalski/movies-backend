package pl.asbt.movies.logger.domain;

import java.util.ArrayList;
import java.util.List;

public class MovieLogger {
    private static MovieLogger movieLogger = null;
//    private String lastLog = "";
    private List<String> movielogs = new ArrayList<>();

    private MovieLogger() {
    }

    public static MovieLogger getInstance() {
        if (movieLogger == null) {
            synchronized(MovieLogger.class) {
                if (movieLogger == null) {
                    movieLogger = new MovieLogger();
                }
            }
        }
        return movieLogger;
    }

    public void log(String log) {
        movielogs.add(log);
    }

    public String getLastLog() {
        return movielogs.get(movielogs.size()-1);
    }

    public List<String> getAllLogs() {
        return movielogs;
    }

    public void clearAllLogs() {
        movielogs.clear();
    }

    /*public void log(String log) {
        lastLog = log;
        System.out.println("Log: [" + log + "]");
    }

    public String getLastLog() {
        return lastLog;
    }*/
}

