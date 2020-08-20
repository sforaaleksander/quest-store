package com.codecool.queststore.view;

import com.codecool.queststore.control.services.models.TradableObject;
import com.codecool.queststore.dao.user.User;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;

public class StudentView {

    public void loadMainPageTemplateWithStudent(HttpExchange exchange, User student) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/studentMain.twig");
        JtwigModel model = JtwigModel.newModel().with("student", student);
        String response = template.render(model);
    }

    public void loadTemplateWithAllQuest(HttpExchange exchange, List<TradableObject> allQuests) {

    }

    public void loadTemplateWithAllArtifacts(HttpExchange exchange, List<TradableObject> allItems) {

    }
}
