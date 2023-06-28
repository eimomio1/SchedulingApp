package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {
    private int id;
    private String description;
    private String location;
    private String title;
    private String date;
    private String start;
    private String end;
    private String name;

    // Constructor
    public User(int id, String description, String location, String title, String date, String start, String end,
                String name) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.title = title;
        this.date = date;
        this.start = start;
        this.end = end;
        this.name = name;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getName() {
        return name;
    }
}
