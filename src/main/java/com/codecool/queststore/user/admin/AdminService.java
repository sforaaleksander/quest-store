package com.codecool.queststore.user.admin;

import com.codecool.queststore.user.mentor.Mentor;
import com.google.gson.Gson;

import java.util.List;

public class AdminService {

    private Gson gson = new Gson();
    private AdminDao adminDao = new AdminDao();

    void createMentorAccount(String json) {
        adminDao.createMentorAccount(gson.fromJson(json, Mentor.class));
    }

    public void createClassroom(String json) {
        /// admin dao save classroom
    }

    public void editMentorProfile(String json) {
        /// admin dao edit mentor
    }

    public List<Mentor> getMentors(String json) {
        return null;
    }

    public List<Mentor> getMentors() {
        return null;
    }

    public Object getAdminInfo(String json) {
        return null;
    }
}
