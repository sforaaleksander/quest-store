package com.codecool.queststore.control.services;

import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.classrooms.Classroom;
import com.codecool.queststore.dao.classrooms.ClassroomDao;
import com.codecool.queststore.dao.roles.Role;
import com.codecool.queststore.dao.roles.RoleDao;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.dao.user.UserDao;
import com.codecool.queststore.dao.userClassrooms.UserClassroom;
import com.codecool.queststore.dao.userClassrooms.UserClassroomDao;

import java.util.List;

public class UserService {

    private Dao<User> userDao = new UserDao();
    private Dao<Role> roleDao = new RoleDao();
    private Dao<Classroom> classroomDao = new ClassroomDao();
    private Dao<UserClassroom> userClassroomDao = new UserClassroomDao();
    private final int MENTOR_ROLE_ID = roleDao.get("name='mentor' LIMIT 1").get(0).getId();
    private final int STUDENT_ROLE_ID = roleDao.get("name='student' LIMIT 1").get(0).getId();

    public boolean addStudent(String name, String surname, String password, String email) {
        return addUser(name, surname, password, email, STUDENT_ROLE_ID);
    }

    public boolean addMentor(String name, String surname, String password, String email) {
        return addUser(name, surname, password, email, MENTOR_ROLE_ID);
    }

    private boolean addUser(String name, String surname, String password, String email, int roleId) {
        return userDao.insert(new User().setName(name).setSurname(surname).setPassword(password)
                .setEmail(email).setIdRole(roleId).setActive(true));
    }

    public boolean editMentorProfile(int mentorId, String name, String surname, String password,
                                  String email, boolean isActive) {
        User mentorToEdit = userDao.get(String.format("id=%d LIMIT 1", mentorId)).get(0);
        mentorToEdit.setName(name).setSurname(surname).setPassword(password)
                .setEmail(email).setActive(isActive);
        return userDao.update(mentorToEdit);
    }

    public User getMentor(int mentorId) {
        return userDao.get(String.format("id=%d AND id_role=%d LIMIT 1)", mentorId, MENTOR_ROLE_ID)).get(0);
    }

    public List<User> getAllMentors() {
        return userDao.get(String.format("id_role=%d)", MENTOR_ROLE_ID));
    }

    public boolean createClassroom(String classroomName) {
        return classroomDao.insert(new Classroom().setName(classroomName));
    }

    public boolean addUserToClassroom(int userId, String classroomName) {
        int classroomId = classroomDao.get(
                String.format("name='%s' LIMIT 1", classroomName)).get(0).getId();
        return userClassroomDao.insert(new UserClassroom()
                .setUserId(userId).setClassroomId(classroomId));
    }

}
