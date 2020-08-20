package com.codecool.queststore.control.services.models;

public enum UserRoleType {
    STUDENT(1),
    MENTOR(2),
    ADMIN(3);

    private final int id;

    UserRoleType(int id) {
        this.id = id;
    }

    public static UserRoleType getById(int id) {
        for (UserRoleType role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("No such role type");
    }

    public int getId() {
        return id;
    }
}
