package com.codecool.queststore.dao.categories;

public class Category {
    private int id;
    private String name;

//    public Category(int id, String name) {
//        this.id = id;
//        this.name = name;
//    }
    public Category() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category setId(int id) {
        this.id = id;
        return this;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

//    public Category createCategory() {
//        return new Category(id, name);
//    }
}
