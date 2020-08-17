package com.codecool.queststore.user.admin;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;

public class AdminController implements HttpHandler {

    private AdminService adminService = new AdminService();
    private AdminView adminView = new AdminView();


    @Override
    public void handle(HttpExchange exchange) throws IOException {



    }

    private File getStartScreen(String json) {
        return adminView.getStartScreenTemplate(adminService.getAdminInfo(json));
    }

    private void createMentorAccount(String json) {
        adminService.createMentorAccount(json);
    }

    private void createClassroom(String json) {
        adminService.createClassroom(json);
    }

    private void editMentorProfile(String json) {
        adminService.editMentorProfile(json);
    }

    private File seeMentorProfile(String json) {
        return adminView.createMentorsTemplate(adminService.getMentors(json));
    }

    private File seeMentors() {
        return adminView.createMentorsTemplate(adminService.getMentors());
    }

}
