package com.codecool.queststore.view;

import com.codecool.queststore.dao.user.User;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;

public class AdminView {
    public void loadMainPageTemplateWithAdmin(HttpExchange exchange, User admin) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/adminMain.twig");
        JtwigModel model = JtwigModel.newModel().with("admin", admin);
        String response = template.render(model);
    }

    public void loadTemplateWithAllMentors(HttpExchange exchange, List<User> mentors) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/mentorsList.twig");
        JtwigModel model = JtwigModel.newModel().with("mentors", mentors);
        String response = template.render(model);
    }

    public void loadTemplateWithEditMentor(HttpExchange exchange, User mentor) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/editMentor.twig");
        JtwigModel model = JtwigModel.newModel().with("mentor", mentor);
        String response = template.render(model);
    }
}
