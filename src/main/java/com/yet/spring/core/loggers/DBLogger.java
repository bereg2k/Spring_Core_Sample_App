package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import com.yet.spring.core.utils.DbUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DBLogger implements EventLogger {
    private JdbcTemplate jdbcTemplate;

    public DBLogger(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        try {
            jdbcTemplate.execute("SELECT * FROM events");
        } catch (DataAccessException e) {
            jdbcTemplate.execute("CREATE TABLE events " +
                    "(" +
                    "ID INT, " +
                    "DATE TIMESTAMP," +
                    "MESSAGE VARCHAR(255)" +
                    ")");
        }
    }

    public void destroy() {
        System.out.println("OBJECTS FROM DB:");
        DbUtils.printDbContents(getEvents());
    }

    @Override
    public void logEvent(Event event) {
        jdbcTemplate.update("INSERT INTO events (ID, DATE, MESSAGE) VALUES (?, ?, ?)",
                event.getId(), event.getDate(), event.getMsg());
    }

    public List<Event> getEvents() {
        List<Event> eventsList = jdbcTemplate.query("SELECT * FROM events", new RowMapper<Event>() {
            @Override
            public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("ID");
                Date date = rs.getTimestamp("DATE");
                String message = rs.getString("MESSAGE");
                return new Event(id, new Date(date.getTime()), message);
            }
        });

        return eventsList;
    }
}
