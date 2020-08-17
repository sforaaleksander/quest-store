package com.codecool.queststore.user.student;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;

public class StudentController implements HttpHandler {

    private StudentService studentService = new StudentService();
    private StudentView studentView = new StudentView();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }

    private File getStartScreen(String json) {
        return studentView.getStartScreenTemplate(studentService.getStudentInfo(json));
    }

    private File seeWallet(String json) {
        return studentView.getStudentWalletTemplate(studentService.getWallet(json));
    }

    private void buyArtifact(String json) {
        studentService.buyArtifact(json);
    }

    private File seeExperienceLevel(String json) {
        return studentView.createExpirienceLevelTemplate(studentService.getExperienceLevel(json));
    }

}
