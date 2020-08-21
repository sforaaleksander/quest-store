package com.codecool.queststore.control.services;

import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.session.Session;
import com.codecool.queststore.dao.session.SessionDao;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.dao.user.UserDao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class LoginService {
    private final int sessionDurationMinutes = 10;
    private Dao<Session> sessionDao = new SessionDao();
    private Dao<User> userDao = new UserDao();

    public Optional<String> login(String email, String password) {
        if (!isUserAndPasswordValid(email, password)) {
            return Optional.empty();
        }

        String sessionId = getRandomString();
        var logInDate = Timestamp.valueOf(LocalDateTime.now());
        var expirationDate = Timestamp.valueOf(LocalDateTime.now().plusMinutes(sessionDurationMinutes));
        boolean active = true;
        int userId = userDao.get(String.format("email = '%s'", email)).get(0).getId();

        sessionDao.insert(new Session(sessionId, userId, logInDate, expirationDate, active));

        return Optional.of(sessionId);
    }

    private boolean isUserAndPasswordValid(String email, String password) {
        var users = userDao.get(String.format("email = '%s'", email));
        if (users.isEmpty()) {
            return false;
        }

        return password.equals(users.get(0).getPassword());
    }

    private String getRandomString() {
        final int length = 20;
        StringBuilder sb = new StringBuilder(20);

        for (int i = 0; i < length; i++) {
            sb.append(getRandomChar());
        }
        return sb.toString();
    }

    private char getRandomChar() {
        final int start = 48;
        final int end = 91;
        return (char) ThreadLocalRandom.current().nextInt(start, end + 1);
    }

    public void logout(User user) {
        List<Session> sessions = getActiveSessionsByUser(user);
        if (sessions.isEmpty()) {
            throw new NullPointerException("Cannot logout user.");
        }
        var thisSession = sessions.get(0);
        thisSession.setActive(false);
        sessionDao.update(thisSession);
    }

    private List<Session> getActiveSessionsByUser(User user) {
        return sessionDao.get(String.format("user_id = %d AND is_active = true", user.getId()));
    }

    public void logoutNonActiveUsers() {
        var sessions = sessionDao.get("logout_timestamp < NOW() AND is_active = true");
        for (Session session : sessions) {
            session.setActive(false);
            sessionDao.update(session);
        }
    }

    public void extendLoginTime(User user) {
        var activeSession = getActiveSessionsByUser(user).get(0);
        activeSession.setLogoutTimestamp(Timestamp.valueOf(LocalDateTime.now().plusMinutes(sessionDurationMinutes)));
        sessionDao.update(activeSession);
    }

    public Optional<User> getLoggedUserBySessionId(String sessionId) {
        logoutNonActiveUsers();
        List<Session> sessions = sessionDao.get(
                String.format("session_id = '%s' AND is_active=true", sessionId));
        if (sessions.isEmpty()) return Optional.empty();
        return Optional.of(userDao.get(String.format("id = %d", sessions.get(0).getUserId())).get(0));
    }
}
