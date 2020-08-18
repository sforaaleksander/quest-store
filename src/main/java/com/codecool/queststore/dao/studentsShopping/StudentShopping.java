package com.codecool.queststore.dao.studentsShopping;

import java.sql.Date;

public class StudentShopping {
    private int userId;
    private int shoppingId;
    private java.sql.Date confirmedDate;
    private boolean isConfirmed;

    public StudentShopping(int userId, int shoppingId, java.sql.Date confirmedDate, boolean isConfirmed) {
        this.userId = userId;
        this.shoppingId = shoppingId;
        this.confirmedDate = confirmedDate;
        this.isConfirmed = isConfirmed;
    }

    public int getUserId() {
        return userId;
    }

    public int getShoppingId() {
        return shoppingId;
    }

    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
