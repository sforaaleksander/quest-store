package com.codecool.queststore.dao.classrooms;

public class Classroom {

    private int id;
    private String name;

    public Classroom(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
