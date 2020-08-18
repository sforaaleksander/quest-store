package com.codecool.queststore.dao.quests;

public class Quest {

    private int id;
    private String name;
    private String description;
    private int cost;
    private int categoryId;

    public Quest(int id, String name, String description, int cost, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
