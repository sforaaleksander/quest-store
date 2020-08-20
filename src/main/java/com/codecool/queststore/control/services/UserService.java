package com.codecool.queststore.control.services;

import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.balance.Balance;
import com.codecool.queststore.dao.balance.BalanceDao;
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

    public boolean addStudent(User student) {
        boolean isAdded = addUser(student, STUDENT_ROLE_ID);
        if (isAdded) {
            new BalanceDao().insert(new Balance().setUserId(userDao.get(
                    String.format("email='%s' AND password='%s'",
                            student.getEmail(), student.getPassword())).get(0).getId())
                    .setAmount(0).setTotalEarned(0));
        }
        return isAdded;
    }

    public boolean addMentor(User mentor) {
        return addUser(mentor, MENTOR_ROLE_ID);
    }

    private boolean addUser(User user, int roleId) {
        return userDao.insert(new User().setName(user.getName()).setSurname(user.getSurname())
                .setPassword(user.getPassword()).setEmail(user.getEmail())
                .setIdRole(roleId).setActive(true));
    }

    public boolean editMentorProfile(User mentor) {
        User mentorToEdit = userDao.get(String.format("id=%d LIMIT 1", mentor.getId())).get(0);
        mentorToEdit.setName(mentor.getName()).setSurname(mentor.getSurname())
                .setPassword(mentor.getPassword()).setEmail(mentor.getEmail())
                .setActive(mentor.isActive());
        return userDao.update(mentorToEdit);
    }

    public User getStudent(int studentId) {
        return userDao.get(String.format("id=%d AND id_role=%d LIMIT 1)", studentId, STUDENT_ROLE_ID)).get(0);
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

    public List<Classroom> getAllClassrooms() {
        return classroomDao.get("1=1");
    }

    public List<User> getAllStudents() {
        return userDao.get(String.format("id_role=%d)", STUDENT_ROLE_ID));
    }
}
