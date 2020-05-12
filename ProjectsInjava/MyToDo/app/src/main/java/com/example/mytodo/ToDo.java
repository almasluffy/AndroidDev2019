package com.example.mytodo;

import java.io.Serializable;
import java.util.*;

public class ToDo implements Serializable {
    private String name;
    private String description;
    private Date date;

    public ToDo(String name, String description){
        this.name = name;
        this.description = description;
        this.date = new Date();
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

    public void setDate(Date date) {
        this.date = date;
    }
}
