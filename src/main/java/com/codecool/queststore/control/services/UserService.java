package com.codecool.queststore.control.services;

import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.PostgreSqlJDBC;
import com.codecool.queststore.dao.roles.Role;
import com.codecool.queststore.dao.roles.RoleDao;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.dao.user.UserDao;

public class UserService {

    private Dao<User> userDao = new UserDao();
    private Dao<Role> roleDao = new RoleDao();

    public void addStudent(String name, String surname, String password, String email) {
        addUser(name, surname, password, email, roleDao.get("name='student' LIMIT 1").get(0).getId());
    }

    public void addMentor(String name, String surname, String password, String email) {
        addUser(name, surname, password, email, roleDao.get("name='mentor' LIMIT 1").get(0).getId());
    }

    private void addUser(String name, String surname, String password, String email, int roleId) {
        userDao.insert(new User().setName(name).setSurname(surname).setPassword(password)
                .setEmail(email).setIdRole(roleId).setActive(true));
    }

    public void editMentorProfile(int mentorId, String name, String surname, String password,
                                  String email, boolean isActive) {
        User mentorToEdit = userDao.get(String.format("id=%d LIMIT 1", mentorId)).get(0);
        mentorToEdit.setName(name).setSurname(surname).setPassword(password)
                .setEmail(email).setActive(isActive);
        userDao.update(mentorToEdit);
    }

    public Object seeMentorProfile(String data) {
        return null;
    }

    public void createClassroom(String data) {

    }

    public void addUserToClassroom(String... data) {

    }

}
