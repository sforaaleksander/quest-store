package com.codecool.queststore.dao.groupShopping;

import java.sql.Date;

public class GroupShopping {
    private int id;
    private int itemId;
    private boolean confirmed;
    private Date startedDate;
    private boolean isPaid;
    private Date paidDate;
    private boolean isUsed;

    public GroupShopping(int id, int itemId, boolean confirmed, Date startedDate, boolean isPaid, Date paidDate, boolean isUsed) {
        this.id = id;
        this.itemId = itemId;
        this.confirmed = confirmed;
        this.startedDate = startedDate;
        this.isPaid = isPaid;
        this.paidDate = paidDate;
        this.isUsed = isUsed;
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
