package com.codecool.queststore;

import com.codecool.queststore.dao.user.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestUri = exchange.getRequestURI().getPath();
        List<String> uriList = Arrays.stream(requestUri.split("/"))
                .collect(Collectors.toList());
        String context = uriList.get(uriList.size() - 1);

        if (!"test".equals(context)) {
            return;
        }

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/studentMain.twig");
        User user = new User();
        user.setIdRole(1)
                .setId(1)
                .setPassword("asd")
                .setActive(true)
                .setEmail("mateusz@gmail.com")
                .setName("Mateusz")
                .setSurname("Golda");
        JtwigModel model = JtwigModel.newModel().with("student", user);

        String response = template.render(model);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
