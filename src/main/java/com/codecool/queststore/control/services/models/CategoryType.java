package com.codecool.queststore.control.services.models;

public enum CategoryType {

    BASIC_QUEST("basic-quest"),
    EXTRA_QUEST("extra-quest"),
    BASIC_ITEM("basic-item"),
    MAGIC_ITEM("magic-item");

    private final String name;

    private CategoryType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

}
