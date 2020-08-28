package com.codecool.queststore.control.services;

import com.codecool.queststore.dao.session.Session;
import com.codecool.queststore.dao.session.SessionDao;

public class SessionService {

    private final SessionDao sessionDao;

    public SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public void delete(String sessionId) {
        sessionDao.delete(sessionDao.get(String.format("session_id = '%s'", sessionId)).get(0));
    }

    public void insert(Session session) {
        sessionDao.insert(session);
    }
}
