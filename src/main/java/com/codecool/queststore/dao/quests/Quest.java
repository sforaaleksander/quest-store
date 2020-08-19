package com.codecool.queststore.dao.quests;

public class Quest {

    private int id;
    private String name;
    private String description;
    private int cost;
    private int categoryId;

//    public Quest(int id, String name, String description, int cost, int categoryId) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.cost = cost;
//        this.categoryId = categoryId;
//    }
    public Quest(){
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

    public Quest setId(int id) {
        this.id = id;
        return this;
    }

    public Quest setName(String name) {
        this.name = name;
        return this;
    }

    public Quest setDescription(String description) {
        this.description = description;
        return this;
    }

    public Quest setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public Quest setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

//    public Quest createQuest() {
//        return new Quest(id, name, description, cost, categoryId);
//    }
}
