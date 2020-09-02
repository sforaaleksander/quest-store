package com.codecool.queststore.dao.user;

import com.codecool.queststore.dao.DatabaseResetter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    static UserDao userDao;
    static DatabaseResetter databaseResetter = new DatabaseResetter();

    @BeforeEach
    void resetDb() throws SQLException {
        databaseResetter.resetDatabase();
        userDao = new UserDao("jdbc:h2:~/test_db", "test", "");
    }

    @Test
    void should_ReturnSingleUser_when_CorrectConditionProvided() {
        User user = new User().setId(1).setName("Mateusz").setSurname("Gołda").setPassword("asd").setEmail("mateusz@gmail.com").setIdRole(1).setActive(true);
        List<User> users = new ArrayList<>();
        users.add(user);
        assertEquals(users.toString(), userDao.get("id=1").toString());
    }

    @Test
    void should_ReturnMultipleUsersWithRole2_when_CorrectConditionProvided() {
        User user1 = new User().setId(31).setName("Dominik").setSurname("Starzyk").setPassword("123").setEmail("dominikstarzyk@codecool.com").setIdRole(2).setActive(true);
        User user2 = new User().setId(32).setName("Wojciech").setSurname("Makieła").setPassword("123").setEmail("wojciechmakiela@codecool.com").setIdRole(2).setActive(true);
        User user3 = new User().setId(33).setName("Marcin").setSurname("Izworski").setPassword("123").setEmail("marcinizworski@codecool.com").setIdRole(2).setActive(true);
        User user4 = new User().setId(34).setName("Adam").setSurname("Panasiuk").setPassword("123").setEmail("adampanasiuk@gmail.com").setIdRole(2).setActive(false);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        assertEquals(users.toString(), userDao.get("id_role=2").toString());
    }

    @Test
    void should_ReturnEmptyList_when_ConditionIsNotProvided() {
        assertEquals(Collections.emptyList(), userDao.get(""));
    }

    @Test
    void should_ReturnEmptyList_when_WrongConditionProvided() {
        assertEquals(Collections.emptyList(), userDao.get("id=999"));
    }

    @Test
    void should_ReturnTrueAndInsertUserIntoDatabase_when_CorrectUserObjectProvided() {
        User user = new User().setId(99999).setName("Tester").setSurname("Test").setPassword("12345").setEmail("test@test.com").setIdRole(1).setActive(true);
        assertTrue(userDao.insert(user));
    }

    @Test
    void should_ReturnFalse_when_EmptyUserObjectProvidedWhileInserting() {
        User user = new User();
        assertFalse(userDao.insert(user));
    }

    @Test
    void should_ReturnFalse_when_UserObjectProvidedWithIncompleteDataWhileInserting() {
        User user = new User().setName("stefan");
        assertFalse(userDao.insert(user));
    }

    @Test
    void should_ReturnTrue_when_UserDataSuccessfullyUpdated() {
        User user = new User().setId(1).setName("Mateuszeeeeek").setSurname("Gołda").setPassword("asd").setEmail("mateusz@gmail.com").setIdRole(1).setActive(true);
        assertTrue(userDao.update(user));
    }

    @Test
    void should_ReturnUserWithChangedData_when_UserProvidedWithMultipleChangedFields() {
        User user = new User().setId(1).setName("Mateuszeeeeek").setSurname("Gołdaaaaaa").setPassword("asdaaaa").setEmail("mateusz@gmail.com").setIdRole(1).setActive(true);
        userDao.update(user);
        assertEquals(user.toString(), userDao.get("id=1").get(0).toString());
    }

    @Test
    void should_ReturnEmptyListOfUsersAffectedByUpdate_when_EmptyUserObjectProvidedWhileUpdating() {
        User user = new User();
        userDao.update(user);
        assertEquals(Collections.emptyList(), userDao.get(String.format("id=%s", user.getId())));
    }

    @Test
    void should_ReturnUserWithChangedData_when_ProvidingSingleChangeToUser() {
        User user = new User().setId(1).setName("Mateuszeeeeek").setSurname("Gołda").setPassword("asd").setEmail("mateusz@gmail.com").setIdRole(1).setActive(true);
        userDao.update(user);
        assertEquals(user.toString(), userDao.get("id=1").get(0).toString());
    }

    @Test
    void should_ReturnNullPointerException_when_UserAsNullProvidedToUpdate() {
        User user = null;
        assertThrows(NullPointerException.class, () -> userDao.update(user));
    }

    @Test
    void should_ReturnEmptyListOfUsersAffectedByUpdate_when_ProvidedUserNotExistingInDatabase() {
        User user = new User().setId(9876).setName("Mateuszeeeeek").setSurname("Gołda").setPassword("asd").setEmail("mateusz@gmail.com").setIdRole(1).setActive(true);
        userDao.update(user);
        assertEquals(Collections.emptyList(), userDao.get(String.format("id=%s", user.getId())));
    }

    @Test
    void should_ReturnEmptyListOfUsers_when_DeletingProvidedUser() {
        User user = new User().setId(1).setName("Mateusz").setSurname("Gołda").setPassword("asd").setEmail("mateusz@gmail.com").setIdRole(1).setActive(true);
        userDao.delete(user);
        assertEquals(Collections.emptyList(), userDao.get("id=1"));
    }

    @Test
    void should_ReturnEmptyListOfUsers_when_DeletingEmptyUser() {
        User user = new User();
        userDao.delete(user);
        assertEquals(Collections.emptyList(), userDao.get(String.format("id=%s", user.getId())));
    }
}