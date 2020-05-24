package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;

import java.util.ArrayList;
import java.util.List;

public class CacheFileEventLogger extends FileEventLogger {
    public static final int DEFAULT_CACHE_SIZE = 2;

    private int cacheSize;
    private List<Event> cache;

    public CacheFileEventLogger(String fileName) {
        this(fileName, DEFAULT_CACHE_SIZE);
    }

    public CacheFileEventLogger(String fileName, int cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
        this.cache = new ArrayList<>();
    }

    @Override
    public void logEvent(Event event) {
        cache.add(event);

        if (cache.size() == cacheSize) {
            cache.forEach(super::logEvent);
            cache.clear();
        }
    }

    public void destroy() {
        if (!cache.isEmpty()) {
            cache.forEach(super::logEvent);
        }
    }
}
