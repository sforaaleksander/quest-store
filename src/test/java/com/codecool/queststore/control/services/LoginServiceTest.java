package com.codecool.queststore.control.services;

import com.codecool.queststore.dao.DatabaseResetter;
import com.codecool.queststore.dao.session.SessionDao;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.dao.user.UserDao;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class LoginServiceTest {

    static DatabaseResetter databaseResetter = new DatabaseResetter();

    static LoginService loginService;
    static SessionDao sessionDao;
    static UserDao userDao;

    @BeforeAll
    static void beforeAll() throws IOException, SQLException {
        databaseResetter.resetDatabase();
        userDao = new UserDao("jdbc:h2:~/test_db", "test", "");
        sessionDao = new SessionDao("jdbc:h2:~/test_db", "test", "");
        loginService = new LoginService(sessionDao, userDao);
    }


    @Test
    void should_ReturnEmptyOptional_When_WrongCredentialsProvided() {
        assertEquals(Optional.empty(), loginService.login("xxx","xxxx"));
    }


    @Ignore
    void should_ReturnOptionalOfUser_when_SessionIDExistsInDB() {
        User user = new User().setId(1).setName("Mateusz").setSurname("Gołda").setPassword("asd").setEmail("mateusz@gmail.com").setIdRole(1).setActive(true);
        assertEquals(Optional.of(user), loginService.getLoggedUserBySessionId("1234567890qwerty"));
    }
}