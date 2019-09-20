package com.example.queryapplication.model;

public class TeacherModel {
    private String name;
    private String query;


    public TeacherModel(String query, String name) {
        this.name = name;
        this.query = query;

    }

    public String getName() {
        return name;
    }

    public String getQuery() {
        return query;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}