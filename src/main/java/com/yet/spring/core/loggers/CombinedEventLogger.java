package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;

import java.util.List;

public class CombinedEventLogger implements EventLogger {
    private List<EventLogger> loggers;

    public CombinedEventLogger(List<EventLogger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event) {
        loggers.forEach((logger) -> logger.logEvent(event));
    }

    public EventLogger getLogger(Class<?> clazz) throws IllegalArgumentException {
        for (EventLogger logger : loggers) {
            if (logger.getClass().equals(clazz)) {
                return logger;
            }
        }
        throw new IllegalArgumentException("There's no such logger here!");
    }
}
