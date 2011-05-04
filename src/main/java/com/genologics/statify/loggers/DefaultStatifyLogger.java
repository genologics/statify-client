package com.genologics.statify.loggers;

/**
 * @author Adam Jordens
 */
public class DefaultStatifyLogger implements StatifyLogger {
    @Override
    public void info(String message) {
        System.out.println("STATIFY ## " + message);
    }

    @Override
    public void error(String message) {
        this.error(message, null);
    }

    @Override
    public void error(String message, Exception exception) {
        System.err.println("STATIFY ## " + message);
        if (exception != null) {
            exception.printStackTrace(System.err);
        }
    }
}
