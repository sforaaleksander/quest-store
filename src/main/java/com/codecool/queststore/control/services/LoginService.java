package com.codecool.queststore.control.services;

import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.session.Session;
import com.codecool.queststore.dao.session.SessionDao;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.dao.user.UserDao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class LoginService {
    private Dao<Session> sessionDao = new SessionDao();
    private Dao<User> userDao = new UserDao();

    public Optional<String> login(User user) {
        final int sessionDurationMinutes = 1;
        String sessionId = null;
        boolean passwordMatches = false;

        String email = user.getEmail();
        String password = user.getPassword();
        User loggedUser = userDao.get(String.format("email = %s", email)).get(0);

        if (loggedUser.getPassword().equals(password)) {
            passwordMatches = true;
        }

        sessionId = getRandomString();
        var logInDate = Timestamp.valueOf(LocalDateTime.now());
        var expirationDate = Timestamp.valueOf(LocalDateTime.now().plusMinutes(sessionDurationMinutes));
        boolean active = true;

        sessionDao.insert(new Session(sessionId, loggedUser.getId(), logInDate, expirationDate, active));

        return Optional.ofNullable(sessionId);
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
        final int end = 122;
        return (char) ThreadLocalRandom.current().nextInt(start, end + 1);
    }

    public void logout() {

    }

    public void logoutNonActiveUsers() {

    }

    public void extendLoginTime(String... data) {

    }

    public void getLoggedUserBySessionId() {

    }

}
