package com.yet.spring.core;

import com.yet.spring.core.aspects.LoggingStatsAspect;
import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.loggers.EventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {
    public static final String EVENT_BEAN = "event";
    public static final String SOME_EVENT_FOR_USER = "Some event for user ";

    private Client client;
    private EventLogger defaultLogger;
    private Map<EventType, EventLogger> loggers;
    private LoggingStatsAspect statsAspect;

    public App(Client client, EventLogger defaultLogger,
               Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.loggers = loggers;
    }

    public void setStatsAspect(LoggingStatsAspect statsAspect) {
        this.statsAspect = statsAspect;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = ctx.getBean("app", App.class);

        System.out.println(app.client.getGreeting());

        Event eventUserOne = ctx.getBean(EVENT_BEAN, Event.class);
        eventUserOne.setMsg(SOME_EVENT_FOR_USER + "1");
        Event eventUserTwo = ctx.getBean(EVENT_BEAN, Event.class);
        eventUserTwo.setMsg(SOME_EVENT_FOR_USER + "2");
        Event eventUserThree = ctx.getBean(EVENT_BEAN, Event.class);
        eventUserThree.setMsg(SOME_EVENT_FOR_USER + "3");

        app.logEvent(eventUserOne, EventType.ERROR);
        app.logEvent(eventUserTwo, EventType.INFO);
        app.logEvent(eventUserThree, null);

        app.statsAspect.printStats();

        ctx.close();
    }

    public void logEvent(Event event, EventType eventType) {
        EventLogger logger = loggers.get(eventType);

        if (logger == null) {
            logger = defaultLogger;
        }

        if (event.getMsg().contains(client.getId())) {
            String message = event.getMsg().replaceAll(client.getId(), client.getFullName());
            event.setMsg(message);
        }

        logger.logEvent(event);
    }
}
