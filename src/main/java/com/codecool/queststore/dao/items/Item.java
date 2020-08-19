package com.codecool.queststore.dao.items;

public class Item {

    private int id;
    private String name;
    private String description;
    private int cost;
    private int categoryId;

    public Item() {
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

    public Item setId(int id) {
        this.id = id;
        return this;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public Item setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public Item setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }
}
