package com.codecool.queststore.view;

import com.codecool.queststore.dao.user.User;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class AdminView {
    public void loadMainPageTemplateWithAdmin(HttpExchange exchange, User admin) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/adminMain.twig");
        JtwigModel model = JtwigModel.newModel().with("admin", admin);
        try {
            renderSendGetWriteClose(template, model, exchange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTemplateWithAllMentors(HttpExchange exchange, List<User> mentors) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/mentorsList.twig");
        JtwigModel model = JtwigModel.newModel().with("mentors", mentors);
        try {
            renderSendGetWriteClose(template, model, exchange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTemplateWithEditMentor(HttpExchange exchange, User mentor) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/editMentor.twig");
        JtwigModel model = JtwigModel.newModel().with("mentor", mentor);
        try {
            renderSendGetWriteClose(template, model, exchange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderSendGetWriteClose(JtwigTemplate template, JtwigModel model, HttpExchange exchange) throws IOException {
        String response = template.render(model);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void loadNewClassroomForm(HttpExchange exchange) {
        
    }

    public void displayNewMentorForm(HttpExchange exchange) {
    }
}
