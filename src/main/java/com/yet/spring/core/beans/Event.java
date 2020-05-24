package com.yet.spring.core.beans;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

public class Event {
    private int id;
    private String msg;
    private Date date;
    private DateFormat df;

    public static boolean isDay() {
        int currentHour = LocalDateTime.now().getHour();
        return currentHour >= 8 && currentHour <= 16;
    }

    public Event(int id, Date date, String message) {
        this.id = id;
        this.date = date;
        this.msg = message;
        this.df = DateFormat.getDateTimeInstance();
    }

    public Event(Date date, DateFormat df) {
        this(null, date, df);
    }

    public Event(String msg, Date date, DateFormat df) {
        this.id = Math.abs(new Random().nextInt());
        this.msg = msg;
        this.date = date;
        this.df = df;
    }

    public String getMsg() {
        return msg;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getDateFormattedString() {
        return df.format(date);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", date=" + df.format(date) +
                '}';
    }
}
