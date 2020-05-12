package com.example.tododatabase;

import java.io.Serializable;
import java.util.*;

public class ToDo implements Serializable {
    private int id;
    private String name;
    private String description;
    private Date date;

    public ToDo(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setName(String name) {
        this.name = name;
    }

//    public void setDate(Date date) {
//        this.date = date;
//    }
}