package com.codecool.queststore.dao.session;

import java.sql.Timestamp;

public class Session {

    private String sessionId;
    private int userId;
    private Timestamp loginTimestamp;
    private Timestamp logoutTimestamp;
    private boolean active;

    public Session(String sessionId, int userId, Timestamp loginTimestamp, Timestamp logoutTimestamp, boolean active) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.loginTimestamp = loginTimestamp;
        this.logoutTimestamp = logoutTimestamp;
        this.active = active;
    }

    public String getSessionId() {
        return sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public Timestamp getLoginTimestamp() {
        return loginTimestamp;
    }

    public Timestamp getLogoutTimestamp() {
        return logoutTimestamp;
    }

    public boolean isActive() {
        return active;
    }

    public Session setActive(boolean active) {
        this.active = active;
        return this;
    }

    public Session setLogoutTimestamp(Timestamp logoutTimestamp) {
        this.logoutTimestamp = logoutTimestamp;
        return this;
    }
}
