package com.genologics.statify.loggers;

import com.genologics.statify.Statify;

/**
 * @author Adam Jordens
 */
public class DefaultStatifyLogger implements StatifyLogger {
    public void info(String message) {
        System.out.println("STATIFY ## " + message);
    }

    public void error(String message) {
        System.err.println("STATIFY ## " + message);
    }
}
