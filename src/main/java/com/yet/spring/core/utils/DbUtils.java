package com.yet.spring.core.utils;

import com.yet.spring.core.beans.Event;

import java.util.Comparator;
import java.util.List;

public class DbUtils {

    /**
     * Pretty printing of database's contents
     *
     * @param events event lists from DB
     */
    public static void printDbContents(List<Event> events) {
        int maxIdLength = events.stream()
                .map(Event::getId).max(Comparator.naturalOrder())
                .map(String::valueOf).get().length();
        int maxDateLength = events.stream()
                .map(Event::getDateFormattedString).map(String::length)
                .max(Comparator.naturalOrder()).get();
        int maxMessageLength = events.stream()
                .map(Event::getMsg).map(String::valueOf).map(String::length)
                .max(Comparator.naturalOrder()).get();

        for (int i = 0; i < (maxIdLength - "ID".length()); i++) {
            if (i == (maxIdLength - "ID".length()) / 2) {
                System.out.print("ID");
            }
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < (maxDateLength - "DATE".length()); i++) {
            if (i == (maxDateLength - "DATE".length()) / 2) {
                System.out.print("DATE");
            }
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < (maxMessageLength - "MESSAGE".length()); i++) {
            if (i == (maxMessageLength - "MESSAGE".length()) / 2) {
                System.out.print("MESSAGE");
            }
            System.out.print("-");
        }
        System.out.print("|");
        System.out.println();

        events.forEach((event -> {
                    System.out.print(event.getId());
                    for (int i = 0; i < maxIdLength - String.valueOf(event.getId()).length(); i++) {
                        System.out.print(" ");
                    }
                    System.out.print("|");

                    System.out.print(event.getDateFormattedString());
                    for (int i = 0; i < maxDateLength - event.getDateFormattedString().length(); i++) {
                        System.out.print(" ");
                    }
                    System.out.print("|");

                    System.out.print(event.getMsg());
                    for (int i = 0; i < maxMessageLength - event.getMsg().length(); i++) {
                        System.out.print(" ");
                    }
                    System.out.print("|");

                    System.out.println();
                })
        );
    }
}
