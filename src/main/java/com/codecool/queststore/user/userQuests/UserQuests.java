package com.codecool.queststore.user.userQuests;

import java.sql.Date;

public class UserQuests {
    private int id;
    private int questId;
    private int userId;
    private Date doneDate;
    private boolean accepted;

    int getId() {
        return id;
    }

    UserQuests setId(int id) {
        this.id = id;
        return this;
    }

    int getQuestId() {
        return questId;
    }

    UserQuests setQuestId(int questId) {
        this.questId = questId;
        return this;
    }

    int getUserId() {
        return userId;
    }

    UserQuests setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    Date getDoneDate() {
        return doneDate;
    }

    UserQuests setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
        return this;
    }

    boolean isAccepted() {
        return accepted;
    }

    UserQuests setAccepted(boolean accepted) {
        this.accepted = accepted;
        return this;
    }
}
