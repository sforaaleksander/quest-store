package com.codecool.queststore.dao.user;

public class User {
    private int id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private int idRole;
    private boolean isActive;

    public User() {
    }

    int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    int getIdRole() {
        return idRole;
    }

    public User setIdRole(int idRole) {
        this.idRole = idRole;
        return this;
    }

    boolean isActive() {
        return isActive;
    }

    public User setActive(boolean active) {
        isActive = active;
        return this;
    }
}
