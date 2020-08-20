package com.codecool.queststore.view;

import com.codecool.queststore.dao.classrooms.Classroom;
import com.codecool.queststore.dao.user.User;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class MentorView {

    public void loadMainPageTemplateWithMentor(HttpExchange exchange, User mentor) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/mentorMain.twig");
        JtwigModel model = JtwigModel.newModel().with("mentor", mentor);
        renderSendGetWriteClose(template, model, exchange);
    }

    public void loadTemplateWithAllStudents(HttpExchange exchange, List<User> students) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/studentLists.twig");
        JtwigModel model = JtwigModel.newModel().with("students", students);
        renderSendGetWriteClose(template, model, exchange);
    }

    public void loadTemplateWithAssignStudentToClassroom(HttpExchange exchange, User student, List<Classroom> classrooms) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/studentLists.twig");
        JtwigModel model = JtwigModel.newModel().with("students", student)
                                                .with("classrooms",classrooms);
        renderSendGetWriteClose(template, model, exchange);
    }

    private void renderSendGetWriteClose(JtwigTemplate template, JtwigModel model, HttpExchange exchange) throws IOException {
        String response = template.render(model);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
