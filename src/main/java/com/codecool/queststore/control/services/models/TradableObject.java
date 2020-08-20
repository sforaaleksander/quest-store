package com.codecool.queststore.control.services.models;

public class TradableObject {

    private int id;
    private String name;
    private String description;
    private int cost;
    private String categoryName;
    private TradableObjectType tradableObjectType;

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

    public String getCategoryName() {
        return categoryName;
    }

    public TradableObject setId(int id) {
        this.id = id;
        return this;
    }

    public TradableObject setName(String name) {
        this.name = name;
        return this;
    }

    public TradableObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public TradableObject setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public TradableObject setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public TradableObjectType getTradableObjectType() {
        return tradableObjectType;
    }

    public TradableObject setTradableObjectType(TradableObjectType tradableObjectType) {
        this.tradableObjectType = tradableObjectType;
        return this;
    }
}
