package com.codecool.queststore.dao.userItems;

import java.sql.Date;

public class UserItems {
    private int id;
    private int itemId;
    private int userId;
    private Date boughtDate;
    private boolean isUsed;

    public int getId() {
        return id;
    }

    public UserItems setId(int id) {
        this.id = id;
        return this;
    }

    public int getItemId() {
        return itemId;
    }

    public UserItems setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public UserItems setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Date getBoughtDate() {
        return boughtDate;
    }

    public UserItems setBoughtDate(Date boughtDate) {
        this.boughtDate = boughtDate;
        return this;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public UserItems setUsed(boolean used) {
        isUsed = used;
        return this;
    }
}
