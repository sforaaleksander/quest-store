package com.codecool.queststore.control.services;

import com.codecool.queststore.dao.DatabaseResetter;
import com.codecool.queststore.dao.session.Session;
import com.codecool.queststore.dao.session.SessionDao;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.dao.user.UserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

class LoginServiceTest {

    static DatabaseResetter databaseResetter = new DatabaseResetter();

    static LoginService loginService;
    static SessionDao sessionDao;
    static UserDao userDao;

    @BeforeAll
    static void beforeAll() throws IOException, SQLException {
        databaseResetter.resetDatabase();
        userDao = mock(UserDao.class);
        sessionDao = mock(SessionDao.class);
        when(userDao.getConnection()).thenReturn(databaseResetter.getConnection());
        when(sessionDao.getConnection()).thenReturn(databaseResetter.getConnection());
        loginService = new LoginService(sessionDao, userDao);
    }
//    @BeforeAll
//    static void beforeAll() throws IOException, SQLException {
//        databaseResetter.resetDatabase();
//        userDao = mock(UserDao.class);
//        sessionDao = mock(SessionDao.class);
//        when(userDao.getConnection()).thenReturn(databaseResetter.getConnection());
//        when(sessionDao.getConnection()).thenReturn(databaseResetter.getConnection());
//        loginService = new LoginService(sessionDao, userDao);
//    }

    @Test
    void should_ReturnEmptyOptional_When_WrongCredentialsProvided() {
        assertEquals(Optional.empty(), loginService.login("xxx","xxxx"));
    }


//    @Test todo check if can be anyway tested
//    void should_ReturnCorrectUserAndSessionID_When_CorrectCredentialsProvided() {
//
//        assertEquals(Optional.of(), loginService.login("mateusz@gmail.com","asd"));
//    }

    @Test
    void should_ReturnOptionalOfUser_when_SessionIDExistsInDB() throws ParseException {
        User user = new User().setId(1).setName("Mateusz").setSurname("Gołda").setPassword("asd").setEmail("mateusz@gmail.com").setIdRole(1).setActive(true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate1 = dateFormat.parse("2020-08-31 08:45:46.544");
            Date parsedDate2 = dateFormat.parse("2020-08-31 08:55:50.023");

            Timestamp timestamp1 = new java.sql.Timestamp(parsedDate1.getTime());
            Timestamp timestamp2 = new java.sql.Timestamp(parsedDate2.getTime());

        Session session = new Session("1234567890qwerty", 1, timestamp1, timestamp2, true);
        when(sessionDao.get(String.format("session_id = '%s' AND is_active=true", "1234567890qwerty"))).thenReturn(Collections.singletonList(session));
        when(userDao.get(String.format("id = %d", session.getUserId()))).thenReturn(Collections.singletonList(user));
        assertEquals(Optional.of(user), loginService.getLoggedUserBySessionId("1234567890qwerty"));
    }
}