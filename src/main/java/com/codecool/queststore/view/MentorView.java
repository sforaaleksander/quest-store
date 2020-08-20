package com.codecool.queststore.view;

import com.codecool.queststore.dao.classrooms.Classroom;
import com.codecool.queststore.dao.user.User;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;

public class MentorView {

    public void loadMainPageTemplateWithMentor(HttpExchange exchange, User mentor) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/mentorMain.twig");
        JtwigModel model = JtwigModel.newModel().with("mentor", mentor);
        String response = template.render(model);
    }

    public void loadTemplateWithAllStudents(HttpExchange exchange, List<User> students) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/studentLists.twig");
        JtwigModel model = JtwigModel.newModel().with("students", students);
        String response = template.render(model);
    }

    public void loadTemplateWithAssignStudentToClassroom(HttpExchange exchange, User student, List<Classroom> classrooms) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/studentLists.twig");
        JtwigModel model = JtwigModel.newModel().with("students", student)
                                                .with("classrooms",classrooms);
        String response = template.render(model);
    }
}
