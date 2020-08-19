package com.codecool.queststore.dao.userItems;

import java.sql.Date;

public class UserItems {
    private int id;
    private int itemId;
    private int userId;
    private Date boughtDate;
    private boolean isUsed;

    int getId() {
        return id;
    }

    UserItems setId(int id) {
        this.id = id;
        return this;
    }

    int getItemId() {
        return itemId;
    }

    UserItems setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    int getUserId() {
        return userId;
    }

    UserItems setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    Date getBoughtDate() {
        return boughtDate;
    }

    UserItems setBoughtDate(Date boughtDate) {
        this.boughtDate = boughtDate;
        return this;
    }

    boolean isUsed() {
        return isUsed;
    }

    UserItems setUsed(boolean used) {
        isUsed = used;
        return this;
    }
}
